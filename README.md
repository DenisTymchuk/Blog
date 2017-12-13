**Getting started:**

1. Clone repository `https://github.com/DenisTymchuk/Blog` or download ZIP.
1. Create database **blog** with PostgreSQL.
   * Properties for db **blog** are:
   	  - database name: <em>blog</em>
      - user: <em>user</em> 
      - password: <em>password</em></li>
      - host: <em>localhost</em></li>
      - port: <em>5432</em></li>
1. Fill database by restoring data from file **blog.backup**
1. Run **gradle build** in the folder <em>Blog</em> that you downloaded previously
1. Deploy generated **WAR**-file from _PROJECT_DIR/build/libs/***.war_ the webapps directory, which located in main Tomcat folder
1. Move the directory _PROJECT_DIR/pictures_ into the same directory where you have moved the WAR-file.
1. Run **Tomcat**.	  
