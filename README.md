# TaskSync


TaskSync PMS
Welcome to TaskSync PMS, a project management system I built for my high school computer science class! This app helps teams manage projects, tasks, and comments, with features like user registration, team creation, and task assignment. It’s got a backend built with Java and Spring Boot and a frontend made with React and TypeScript. I worked hard on this, and I’m super proud of how it turned out!
![image](https://github.com/user-attachments/assets/2f4f22a6-8572-43f2-8207-a9925edab5a3)





![image](https://github.com/user-attachments/assets/d4034a2c-89b6-4e1a-9882-755d696eca4d)



Note that if you register as a user, you can only see the team name, you're invited in, the project you're working on and the tasks as well as commenting on the tasks

![image](https://github.com/user-attachments/assets/82fe0b1b-3fd0-4ce2-b97e-032d07e2eab5)

![image](https://github.com/user-attachments/assets/abdb45e9-9de2-466a-8f4f-610d70f085e7)


Features
User Registration and Login: Anyone can sign up as a regular user or a team lead. Team leads get extra powers like creating teams and assigning tasks.
Team Management: Team leads can create teams and add members (other registered users) to them.
Project and Task Tracking: Create projects for teams and add tasks with statuses like “To Do,” “In Progress,” or “Done.” Team leads can assign tasks to team members.
Comments: Users can add comments to tasks to discuss progress or share updates.
Role-Based Access: Regular users can view teams and tasks, but only team leads can create or edit them.
Responsive UI: The frontend is clean and works on both desktop and mobile (kinda, still tweaking it!).
Tech Stack
Backend
Java 17: The main language for the server.
Spring Boot: Handles the API, security, and database stuff.
Spring Security: Manages user authentication with JWT tokens.
PostgreSQL: Stores all the data like users, teams, and tasks.
Maven: For building and managing dependencies.
Frontend
React: Builds the interactive UI.
TypeScript: Makes the JavaScript code safer and easier to debug.
Tailwind CSS: For styling the app (it’s so much easier than plain CSS).
React Router: Handles navigation between pages.
Vite: Super fast build tool for the frontend.
How to Run It
I tested this on my laptop (Windows 11), but it should work on Mac or Linux too. You’ll need Java, Node.js, and PostgreSQL installed.

Prerequisites
Java 17 (I used OpenJDK)
Node.js 18 or later
PostgreSQL 15
Maven
npm (comes with Node.js)
Backend Setup
Clone the repo:
text

Copy
git clone https://github.com/your-username/tasksync-pms.git
cd tasksync-pms
Set up PostgreSQL:
Create a database called tasksync.
Update src/main/resources/application.properties with your database info:
text

Copy
spring.datasource.url=jdbc:postgresql://localhost:5432/tasksync
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
Build and run the backend:
text

Copy
cd backend
mvn clean install
mvn spring-boot:run
The server should start at http://localhost:8080.
Frontend Setup
Open a new terminal and go to the frontend folder:
text

Copy
cd frontend
Install dependencies:
text

Copy
npm install
Run the frontend:
text

Copy
npm run dev
The app should open at http://localhost:8081.
Using the App
Go to http://localhost:8081/register to sign up.
Use TEAM_LEAD as the role to create teams or USER to just join them.
Log in, and you’ll see the teams page. Team leads can create teams and add members.
Click a team to view projects, then a project to see tasks. You can add comments or update tasks if you’re a team lead.
Database Schema
The app uses a few tables:

users: Stores user info (id, first_name, last_name, email, password, role).
teams: Stores team names and IDs.
team_members: Links users to teams (many-to-many).
projects: Stores project names and links to teams.
tasks: Stores tasks with titles, statuses, and assignees.
comments: Stores comments on tasks.
tokens: Stores JWT access and refresh tokens.
Known Issues
Sometimes the team list doesn’t load right after login. Refreshing the page usually fixes it.
The UI isn’t 100% mobile-friendly yet (working on it!).
Error messages could be clearer when something goes wrong.
Future Ideas
Add a search bar to find users when adding team members.
Let team leads remove members from teams.
Add notifications for task updates.
Make the app look prettier with better colors and animations.
Why I Built This
I wanted to learn how to build a full-stack app with real-world features like user authentication and role-based access. It was tough figuring out JWT tokens and React context, but I learned a ton! My teacher said to make something useful, so I thought a project management tool would be cool for group projects.

Contributing
I’m not sure if I want contributions yet since this is my school project, but if you have ideas or find bugs, open an issue on GitHub! Maybe after I get my grade, I’ll open it up for others to help.

License
This is just for my school project, so I’m not putting a real license on it yet. Don’t copy it for your own homework, okay? 😅

Thanks for checking out TaskSync PMS! Hope you like it!
