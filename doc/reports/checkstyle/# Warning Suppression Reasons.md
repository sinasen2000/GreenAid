# Warning Suppression Reasons

## Week 4 Checkstyle Version 2 Suppression Reasons (20190303.jpg)
To connect the database to the server, we need to use the exact same variable names in database tables and Java class fields. Database only allows, for example, last_update but checkstyle enforces the lastUpdate notation. That's why we suppressed variable name declaration warning of Checkstyle.

While there were 43 warnings on checkstyle report, 41 of them were suppressed and only 2 existed on IntelliJ after suppression.

Update: We also solved thos 2 so there was no errors.

## Week 5 Checkstyle Version 2 Suppression Reasons
The same reasons as week 4.