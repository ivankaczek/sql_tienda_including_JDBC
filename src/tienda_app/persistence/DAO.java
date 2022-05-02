/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda_app.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAO {
    
    protected Connection connection;
    protected ResultSet result;
    protected Statement statement;
    
    private final String USER = "root";
    private final String PASSWORD = "root";
    private final String DATABASE = "tienda";
    private final String DRIVER = "com.mysql.jdbc.Driver";
    
  
    protected void connect2DB() throws ClassNotFoundException, SQLException{
        
        try {
            Class.forName(DRIVER);              
                                                // Why am I doing this? Not explained in the video
                                                // Class.forName(); returns the class object associated with the class
                                                // or interface with the given string name.
                                                
            String urlDataBase = "jdbc:mysql://localhost:3306/" + DATABASE + "?useSSL=false";
                                                
                                                // At video#2 8:00 you can check the url root
                                                // Go to 'services' at the navigation bar here to he left
                                                // open 'databases', look for the one corresponding to this project
                                                // 'properties', the url will show the following
                                                // jdbc:mysql://localhost:3306/tienda?zeroDateTimeBehavior=convertToNull
                                                // and now we can change that url to the following line
                                                // jdbc:mysql://localhost:3306/tienda?useSSL=false
                                                // the previous change should desactivate some annoying warnings
                                                // if we pay attention, our String urlDataBase is exctly the concat of the previous
                                                
            connection = DriverManager.getConnection(urlDataBase, USER, PASSWORD);             
            
                                                // Attemps to establish a connection to the given database URL
                                                // Pay attention to import the correct java.sql.(whatever) since there are more than one option
                                                // Video #2 @ 10:28.
                                                
        } catch (ClassNotFoundException | SQLException ex) {
            throw ex;
        }
        
    }
    
    
    // Here follows Video #3
    
    protected void disconnectDataBase() throws Exception {
        try {
            
            if (result != null) {
                result.close();
                /*
                  basically if the ResultSet is not null, it means it 'holds, lodges, posses' some kind of result
                 and if I want to get disconnected I need first to 'kill' every query
                 since the idea here is to get info from my database but then to close the connection
                 because the ammount of possible connection is not infinite and other people might need to use one
                 of those finite available connections
                */
                
            }
            
            if (statement != null) {
                statement.close();
            }
            
            if (connection != null) {
                connection.close();
            }
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    protected void insertDeleteUpdateDataBase(String sqlStatement) throws Exception {
        /*
        steps:
            1. connect to database
            2. execute the query
            3. disconnect from database
        */
        try {
            connect2DB();
            statement = connection.createStatement();
            statement.executeUpdate(sqlStatement);
                /*
                executeUpdate(String sql)
                    Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement 
                    or an SQL statement that returns nothing, such as an SQL DDL statement.
                        Returns:
                            either      (1) the row count for SQL Data Manipulation Language (DML) statements or 
                                        (2) 0 for SQL statements that return nothing
                */
            
            
            
        } catch (SQLException | ClassNotFoundException ex) {
            connection.rollback();
                    /*
                    void	rollback()
                    Undoes all changes made in the current transaction and releases any database locks currently held 
                    by this Connection object.
                    */
            
                    /*
                        Correr el siguiente script en Workbench
                        SET autocommit=1;                               //  this means autocommit = 'true'
                        COMMIT;
                    
                        the code will compile anyway without the rollback method.
                    */
               
            throw ex;
        } finally {
            disconnectDataBase();
        }
    }
    
    protected void queryDataBase(String sqlStatement) throws Exception {
            // this doesn't include a disconnect method, since we will write that somewhere else
        try {
            connect2DB();
            statement = connection.createStatement();
            result = statement.executeQuery(sqlStatement);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
} // here finishes de class

/*
DAO stands for 'data access object'
Remember that it is an abstract class (video #2, @ 04:10)
public abstract class DAO { /code/ }

+---------------+
|   REMEMBER    |
+---------------+

Remember to add the JDBC connection library. Video #2 @ 06:07

We start to code the 3 methods that 'DAO' class will inherit to the offspring
Basically, a method to connect into the Schema, another to get disconnected, and another for the queries.

*/


/*

+-------------------------------------------+
|                                           |
|       EGG Education                       |
|       Agustín Fiordelisi code             |
|                                           |
+-------------------------------------------+

package com.redsocial.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAO {

    protected Connection conexion;
    protected ResultSet resultado;
    protected Statement sentencia;

    private final String USER = "root";
    private final String PASSWORD = "root";
    private final String DATABASE = "perros";
    private final String DRIVER = "com.mysql.jdbc.Driver";

    protected void conectarBase() throws Exception {
        try {
            Class.forName(DRIVER);
            String urlBaseDeDatos = "jdbc:mysql://localhost:3306/" + DATABASE + "?useSSL=false";
            conexion = DriverManager.getConnection(urlBaseDeDatos, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            throw ex;
        }
    }

    protected void desconectarBase() throws Exception {
        try {
            if (resultado != null) {
                resultado.close();
            }
            if (sentencia != null) {
                sentencia.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    protected void insertarModificarEliminar(String sql) throws Exception {
        try {
            conectarBase();
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
        } catch (SQLException ex) {
            // conexion.rollback();
              Descomentar la linea anterior si desean tener en cuenta el rollback.
                Correr el siguiente script en Workbench
            
                SET autocommit=1;
                COMMIT;
            
                **Sin rollback igual anda 
             
            throw ex;
        } finally {
            desconectarBase();
        }
    }

    protected void consultarBase(String sql) throws Exception {
        try {
            conectarBase();
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }
}

*/




/*

+-------------------------------------------+
|                                           |
|       java.sql                            |
|       Interface ResultSet                 |
|                                           |
+-------------------------------------------+


java.sql
Interface ResultSet
	All Superinterfaces:
		AutoCloseable, Wrapper
	All Known Subinterfaces:
		CachedRowSet, FilteredRowSet, JdbcRowSet, JoinRowSet, RowSet, SyncResolver, WebRowSet

public interface ResultSet
extends Wrapper, AutoCloseable

A table of data representing a database result set, which is usually generated by executing a statement that queries the database.
A ResultSet object maintains a cursor (Pronunciation IPA /kɜɹsəɹ/, [ˈkʰɜ˞ sə˞] ) pointing to its current row of data. 
Initially the cursor is positioned before the first row. 
The next method moves the cursor to the next row, and because it returns false when there are no more rows in the ResultSet object, 
it can be used in a while loop to iterate through the result set.

A default ResultSet object is not updatable and has a cursor that moves forward only. 
Thus, you can iterate through it only once and only from the first row to the last row. 
It is possible to produce ResultSet objects that are scrollable and/or updatable. 
The following code fragment, in which con is a valid Connection object, 
illustrates how to make a result set that is scrollable and insensitive to updates by others, and that is updatable. See ResultSet fields for other options.


       Statement stmt = con.createStatement(
                                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = stmt.executeQuery("SELECT a, b FROM TABLE2");
       
	// rs will be scrollable, will not show changes made by others,
       // and will be updatable

 
The ResultSet interface provides getter methods (getBoolean, getLong, and so on) for retrieving column values from the current row. 
Values can be retrieved using either the index number of the column or the name of the column. 
In general, using the column index will be more efficient. 
Columns are numbered from 1. 

For maximum portability, result set columns ( pronunciation: /ˈkɒləm/ ) within each row should be read in left-to-right order, and each column should be read only once.

For the getter methods, a JDBC driver attempts to convert the underlying data to the Java type specified in the getter method and returns a suitable Java value. 
The JDBC specification has a table showing the allowable mappings from SQL types to Java types that can be used by the ResultSet getter methods.

Column names used as input to getter methods are case insensitive. 
When a getter method is called with a column name and several columns have the same name, the value of the first matching column will be returned. 
The column name option is designed to be used when column names are used in the SQL query that generated the result set. 
For columns that are NOT explicitly named in the query, it is best to use column numbers. 
If column names are used, the programmer should take care to guarantee that they uniquely refer to the intended columns, which can be assured with the SQL AS clause.

A set of updater methods were added to this interface in the JDBC 2.0 API (JavaTM 2 SDK, Standard Edition, version 1.2). 
The comments regarding parameters to the getter methods also apply to parameters to the updater methods.

The updater methods may be used in two ways:

to update a column value in the current row. 
In a scrollable ResultSet object, the cursor can be moved backwards and forwards, 
to an absolute position, or to a position relative to the current row. 
The following code fragment updates the NAME column in the fifth row of the ResultSet object rs 
and then uses the method updateRow to update the data source table from which rs was derived.

       rs.absolute(5); // moves the cursor to the fifth row of rs
       rs.updateString("NAME", "AINSWORTH"); // updates the
          // NAME column of row 5 to be AINSWORTH
       rs.updateRow(); // updates the row in the data source

 
to insert column values into the insert row. 
An updatable ResultSet object has a special row associated with it that serves as a staging area for building a row to be inserted. 
The following code fragment moves the cursor to the insert row, builds a three-column row, 
and inserts it into rs and into the data source table using the method insertRow.

       rs.moveToInsertRow(); // moves cursor to the insert row
       rs.updateString(1, "AINSWORTH"); // updates the
          // first column of the insert row to be AINSWORTH
       rs.updateInt(2,35); // updates the second column to be 35
       rs.updateBoolean(3, true); // updates the third column to true
       rs.insertRow();
       rs.moveToCurrentRow();

 
A ResultSet object is automatically closed when the Statement object that generated it is closed, 
re-executed, or used to retrieve the next result from a sequence of multiple results.

The number, types and properties of a ResultSet object's columns are provided by the 
ResultSetMetaData object returned by the ResultSet.getMetaData method.

See Also:
Statement.executeQuery(java.lang.String), Statement.getResultSet(), ResultSetMetaData

+-------------------------------------------+
|                                           |
|       java.sql                            |
|       Interface Statement                 |
|                                           |
+-------------------------------------------+


java.sql
Interface Statement

All Superinterfaces:
AutoCloseable, Wrapper
All Known Subinterfaces:
CallableStatement, PreparedStatement

public interface Statement
    extends Wrapper, AutoCloseable

The object used for executing a static SQL statement and returning the results it produces.

By default, only one ResultSet object per Statement object can be open at the same time. 
Therefore, if the reading of one ResultSet object is interleaved with the reading of another, 
each must have been generated by different Statement objects. 
All execution methods in the Statement interface implicitly close a statment's current ResultSet object 
if an open one exists.

See Also:
Connection.createStatement(), ResultSet

+-------------------------------------------+
|                                           |
|       java.sql                            |
|       Interface Connection                |
|                                           |
+-------------------------------------------+



java.sql
Interface Connection
All Superinterfaces:
AutoCloseable, Wrapper

public interface Connection
extends Wrapper, AutoCloseable
A connection (session) with a specific database. SQL statements are executed and results are returned within the context of a connection.

A Connection object's database is able to provide information describing its tables, its supported SQL grammar, its stored procedures, 
the capabilities of this connection, and so on. This information is obtained with the getMetaData method.

Note: When configuring a Connection, JDBC applications should use the appropriate Connection method such as setAutoCommit or setTransactionIsolation. 
Applications should not invoke SQL commands directly to change the connection's configuration when there is a JDBC method available. 
By default a Connection object is in auto-commit mode, which means that it automatically commits changes after executing each statement. 
If auto-commit mode has been disabled, the method commit must be called explicitly in order to commit changes; otherwise, database changes will not be saved.

A new Connection object created using the JDBC 2.1 core API has an initially empty type map associated with it. 
A user may enter a custom mapping for a UDT in this type map. When a UDT is retrieved from a data source with the method ResultSet.getObject, 
the getObject method will check the connection's type map to see if there is an entry for that UDT. If so, the getObject method will map the UDT to the class indicated. 
If there is no entry, the UDT will be mapped using the standard mapping.

A user may create a new type map, which is a java.util.Map object, make an entry in it, 
and pass it to the java.sql methods that can perform custom mapping. 
In this case, the method will use the given type map instead of the one associated with the connection.

For example, the following code fragment specifies that the SQL type ATHLETES will be mapped to the class Athletes in the Java programming language. 
The code fragment retrieves the type map for the Connection object con, 
inserts the entry into it, and then sets the type map with the new entry as the connection's type map.

      java.util.Map map = con.getTypeMap();
      map.put("mySchemaName.ATHLETES", Class.forName("Athletes"));
      con.setTypeMap(map);
 
See Also:
DriverManager.getConnection(java.lang.String, java.util.Properties), 
Statement, ResultSet, DatabaseMetaD

    +---------------------------+
    |    (Connection) Methods   |
    +---------------------------+

Method:         createStatement()
Returns:        Statement

Creates a Statement object for sending SQL statements to the database.

--------------------------
Vocabulary:

statement (En) = declaración, sentencia (Esp)

*/


