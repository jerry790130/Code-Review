<HTML>
    <HEAD>
       <TITLE>HW6</TITLE>
       <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            text-align: center;
        }
        fieldset{
         display: inline-block;
         text-align: left;
        }
      
        </style>
      <script>
        function varify()
        {
            var loc=document.WeatherSearch.location.value;
            var loc_type=document.WeatherSearch.location_type.value;
            var temp_unit=document.WeatherSearch.tmp_unit;
            if(loc=="")
            {
                alert("Please enter a location.")
                return false;
            }          
            reg=/^\d{5}$/;
            if(reg.test(loc) && loc_type=="City")
            {
                alert("Error input");
                return false;
            }
            else if( loc_type=="ZIP_code")
            {
                if(!reg.test(loc)){
                    alert("invalid ZIP code");
                    return false;
                    }
            }  
            if(!temp_unit[0].checked)
            {
                if(!temp_unit[1].checked)
                {
                    alert("Please select a Temperature Unit.")
                    return false;
                }
            }
            
            
            return true;
        }
    </script>
       
    </HEAD>
    <BODY>
    <H1>Weather Search</H1>
    <fieldset>
        <form name="WeatherSearch" method="POST" border:2px  onsubmit="return varify()" > 
        
        Location:   <input type="text" name="location" maxlength="255" value='<?php if(isset($_POST["location"])){ echo $_POST["location"]; } ?>' /><br/>
        Location Type:<SELECT name="location_type" >
                        <OPTION value="City"<?php  if(isset($_POST["location_type"])){if($_POST['location_type'] =="City" ){ echo "selected";} }?> selected="selected">City
                        <OPTION value="ZIP_code"<?php if(isset($_POST["location_type"])){ if($_POST['location_type'] =="ZIP_code"){ echo "selected";}} ?>>ZIP code
                        </SELECT><br/>
        Temperature Unit:<input type="radio" name="tmp_unit" value="f" <?php  if(isset($_POST["tmp_unit"])){ if($_POST['tmp_unit'] =="f"){ echo "checked";}} ?>>Fahrenheit 
                        <input type="radio" name="tmp_unit" value="c"<?php  if(isset($_POST["tmp_unit"])){if($_POST['tmp_unit'] =="c"){ echo "checked";}} ?>>Celsius</br>
                        <input type="submit" name="Submit" value="Search" >
      </fieldset>
      </br></br>
      </form>
    </BODY>
</HTML>

<?php
    $count_city;
    if(isset($_POST["Submit"]))
    {   
       
    if($_POST["location_type"]=="ZIP_code")
    {
        $clean_location = clearSpace($_POST['location']);
        $get_url="http://where.yahooapis.com/v1/concordance/usps/".$clean_location."?appid=ti_ZY5DV34H9Twta_XdMBOLDKCe85bpTazOj9ylCghDsGJzkDuBKFjbIoxn6BNilJBhI"; 
        
        if(@simplexml_load_file($get_url)){
            $xml=simplexml_load_file($get_url);            
        }else{
            echo "Zero Result found";
            return ;
        }
        
        $count_city=0;
        $xml_result="";
        
        foreach($xml->woeid as $child){
           // $url = 'http://weather.yahooapis.com/forecastrss?'.'w='. $child.'&u='.$_POST['tmp_unit'];
            $xml_result.=parse_xml($child);
        }      
        if( $count_city !=0){
        print_table_ini($count_city,$_POST["location_type"],$_POST['location']);
        print_table($xml_result);
        print_table_end();
        }
        else
        {
            echo "Zero Result found";
        }
    }
    else if($_POST["location_type"]=="City")
    {
        $clean_location = clearSpace($_POST['location']);
        $get_url = 'http://where.yahooapis.com/v1/places'.'$'.'and'."(.q('".$clean_location."'),.type(7));start=0;count=5?appid=ti_ZY5DV34H9Twta_XdMBOLDKCe85bpTazOj9ylCghDsGJzkDuBKFjbIoxn6BNilJBhI";
        
        if(@simplexml_load_file($get_url)){
            $xml=simplexml_load_file($get_url);            
        }else{
            echo "Zero Result found";
            return ;
        }
        
        $count_city=0;        
      $xml_result="";
        
        foreach($xml->place as $child){
            //$url = 'http://weather.yahooapis.com/forecastrss?'.'w='. $child->woeid.'&u='.$_POST['tmp_unit'];
            $xml_result.=parse_xml($child->woeid);        
         } 
        if( $count_city !=0){
            print_table_ini($count_city,$_POST["location_type"],$_POST['location']);      
            print_table($xml_result);
            print_table_end();    
        }
        else
        {
            echo "Zero Result found";
        }
    }
    
    }
    
    function clearSpace($input_string)
    {
        $ans=urlencode(trim($input_string));
        return $ans;
    }
    
    function print_table_ini($c,$loc_type,$clean_location)
    {
        $loc=$loc_type;
        if($loc=="ZIP_code")
            $loc="ZIP code";
        else
            $loc="City";
        
        if($c==1)
            $s=$c." result for ".$loc." ".$clean_location;
        else
            $s=$c." results for ".$loc." ".$clean_location;
        
        echo"<Table border='2' align='center'> <caption>".$s."</caption> <TR> <th>Weather</th> 
            <th>Temperature</th> <th>City</th> <th>Region</th> <th>Country</th> <th>Latitude</th> <th>Longtitude</th> <th>Link to Details</th></tr>";
    }
        
    function parse_xml($woeid)
    {
        $get_xml_url= 'http://weather.yahooapis.com/forecastrss?'.'w='. $woeid.'&u='.$_POST['tmp_unit'];  
        //echo "</br>".$get_xml_url;
        if(@simplexml_load_file($get_xml_url)){
            $xml=simplexml_load_file($get_xml_url);            
        }else{
            echo "Errors";
            return ;
        }  
          
        $print_table="";
    
       if(!preg_match("/Error/i", $xml->channel->title))
        {         
        global $count_city;
           $count_city = $count_city+1;
        $print_table.="<tr>";
        //temp
        $condition=$xml->channel->item->children('yweather',true)->condition->attributes();
       
       //img
        $dcrpt=$xml->channel->item->description;
        $array = array();
        preg_match( '/src="([^"]*)"/i', $dcrpt, $array ) ;
        //print_r( $array[1] );          
        
        $img_url="<a href='".$get_xml_url. "' target='_blank'><img src='".$array[1]."' alt='".$condition->text."' title='".$condition->text."'/> </a>"; 
        
        if($img_url=="")
            $img_url="N/A";           
         $print_table.="<td>".$img_url."</td>";
          
        $t=$xml->channel->children('yweather',true)->units->attributes()->temperature; 
 
         //Temperature
         if($condition->text==""&& $condition->temp=="" && $t=="")
            $tmp="N/A";
        else
            $tmp=$condition->text." ".$condition->temp."<sup>o</sup>".$t;    
         $print_table.="<td>".$tmp."</td>";
        
        //city, regin, country
       $loc=$xml->channel->children('yweather',true)->location->attributes();
        foreach($loc as $a=>$b)
        {   
            if($b=="")
                $b="N/A";
             $print_table.="<td>".$b."</td>";
        }
      
      //lat
        $lat=$xml->channel->item->children('geo',true)->lat;
        if($lat=="")
            $lat="N/A";        
        $print_table.="<td>".$lat."</td>";
        
        //long
       $long=$xml->channel->item->children('geo',true)->long;
       if($long=="")
            $long="N/A";
       $print_table.="<td>".$long."</td>";
        
        //link
        $link="<a href='".$xml->channel->link."' target='_blank'> Details </a>";
        if($link=="")
            $link="N/A";
        $print_table.="<td>".$link."</td>";
        
          $print_table.="</tr>";  
         
    }
    
     return $print_table;
    }
    function print_table($r)
    {
        echo $r;
    }
    function print_table_end()
    {
        echo "</table>";
    }
?>


