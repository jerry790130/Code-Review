
I use  external library "ojdbc6.jar". So, please add classpath when compile and run.

lower left corner = (0, 0)

My database connection name is DBHW, user is SCOTT and passowrd is 445532, please change these things to fit your database when testing.
connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:DBHW", "SCOTT","445532");


