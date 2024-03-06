Library Management Tool

Description:
The Library Management Tool is a Java application developed using Maven and JavaFX GUI (Graphical User Interface). This tool allows librarians or library administrators to manage various aspects of a library, including adding, editing, and deleting books, managing library members, and keeping track of book loans.


Features:


+ **AdminPanel** 
  +   **Book Management:**
    + Add new books to the library database.
    + Delete books from the library database.
    + Extend Loan.
  + **Member Management:**
    + Add new library members
    + Edit existing member details such as role and password.
    +Delete members from the library database.
    +Loan Management
    +Fine Management ( 2.00 per day ) 

+ **UserPanel**
  + Auth user and show only user options 
  + See book loan by logged user 
  + See all books with filter and advanced schearch
  + See Total fine 
  
**To Do:**
- [x] Add GUI with javaFX
- [ ] Make Connection with database like sql or API
- [ ] Make better Gui
- [ ] Add edit system for selected book and users
- [ ] Make basic Release version with more clean code and better functionality 

**How to Use:**
```
git clone https://github.com/Rzelonek/Liblary-Managment.git
```
```
cd library-management-tool
```
```
mvn clean install
```
```
compile with maven and run with java
```
