# Assesment_2
# Library Management System

A console-based Java application designed to simulate a basic library management system. The application handles roles for both Librarians and Students, allowing tracking of user actions, catalog registration, and book checkouts.

---

## Features

### 1. Librarian Services
* Add Book: Register a new book into the repository using a custom title and a unique integer identification number.
* View Books: View a complete log of all books currently managed in the library database along with their availability statuses.

### 2. Student Services
* Borrow Book: Check out a book by entering its specific Book ID. If the book is already checked out or the ID is invalid, the system alerts the student.
* View Books: Browse the existing catalog to see which books are currently Available or Not Available.
* Return Books: Return books borrowed back through the System.

---

## Project Structure

The project uses core Object-Oriented Programming (OOP) architectures including inheritance, abstraction, and dynamic list tracking:

* LibMem (Abstract Class): The foundational blueprint for system users tracking names and IDs.
* Librarian and Student: Specialized classes inheriting from LibMem to handle role-specific profiles.
* Book: Manages state data for individual titles including BookName, BookID, and Borrow_Status.
* LibManSys: The main entry point containing the UI execution loop and logic routers.

---

## How to Run the Application

Follow these steps to run the software locally on your machine:

1. Prerequisites: Ensure you have the Java Development Kit (JDK) installed on your system.
2. Compilation: Open your terminal inside the project directory and compile the source file:
   ```bash
   javac LibManSys.java
