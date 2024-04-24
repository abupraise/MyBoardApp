# MyBoard - Task Management Web Application

### OVERVIEW
MyBoard is a web-based task management application designed to simplify personal to-do's and tracking for individuals. 
It provides a user-friendly platform for organizing tasks, setting deadlines, tracking progress, and prioritizing activities.
It typically offers features such as task creation, assignment, scheduling, collaboration, visualization, and integration 
with other productivity tools.

### FEATURES

**User Authentication and Security**

- User Registration: Allow users to create accounts by providing their names, email, phone number and password.
- User Login: Authenticate users with their credentials (username/email and password) to access the application.
- Password Encryption: Encrypt user passwords to ensure security and prevent unauthorized access to user accounts.
- Session Management: Maintain user sessions and implement session timeout to enhance security.

**Dashboard View**

- Summary View: Displaying tasks, deadlines, progress, and priority levels.
- Personalized Widgets: Quick access to assigned tasks, upcoming deadlines, and completed tasks.

**Task Management**

- List Creation: Ability to create Lists (sections) to contain related tasks.
- Quick Task Creation: Ability to create tasks simply by inputting task titles.
- Update Task: Ability to update task titles
- Detailed Task Creation: Ability to create tasks with titles, descriptions, deadlines, and priority levels.
- Task Tracking: Update task statuses (e.g., in progress, completed).
- Delete Task: User can delete task(s).

### ENTITY RELATIONSHIP DIAGRAM

![Entity Relationship Diagram](/Users/decagon/Downloads/erd.png)

### USER INTERFACE (UI) DESIGN
- Intuitive Design: User-friendly interface for easy navigation.
- Responsive Design: Accessible on desktop.

### INSTALLATION AND SETUP
- Clone the repository: git clone https://github.com/decadevs/myboard_backend.git
- Navigate to the project directory: cd myboard-backend.
- Build and run the backend server.
- Open your web browser and navigate to http://localhost:8080 to access the application.

### USAGE
- Dashboard: Upon logging in, you'll be taken to the dashboard where you can view a summary of your tasks and their details.
- Task Management: Use the provided features to create, edit, delete, and prioritize tasks. 
Organize tasks into lists for better organization.
- Task Tracking: Update task statuses to track progress and ensure timely completion.

### TESTING PROCEDURE
Backend Testing: Unit testing was done using frameworks like JUnit and Mockito for testing service methods, 
to ensure proper functionality and error handling.

### TECHNOLOGIES USED
Frontend: Reactjs, TailwindCSS, JavaScript.
Backend: Java, Spring Boot
Database: PostgresSQL
Authentication: JSON Web Tokens (JWT).
Deployment: Docker, CI/CD.

### CONTRIBUTING
Contributions to the development of MyBoard are welcome! Feel free to fork the repository, make changes, and submit pull requests for review.

### LICENSE
MyBoard is open-source software released under the [MIT Licence](/Users/decagon/Desktop/myboard_backend/LICENCE.txt)

### CONTACT
For any inquiries or feedback regarding MyBoard, please contact us at myboard20@gmail.com.