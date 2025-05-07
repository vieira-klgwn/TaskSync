TaskSync PMS
Welcome to TaskSync PMS, a project management system I built for my high school computer science class! This app is designed to help teams organize projects, manage tasks, and collaborate through comments. It has a backend powered by Java and Spring Boot, paired with a frontend built using React and TypeScript. I put a lot of effort into this, and I’m really excited to share it!

<p align="center"> <img src="https://via.placeholder.com/600x300.png?text=TaskSync+PMS+Demo" alt="TaskSync PMS Screenshot" width="600"/> </p>
🌟 Features
User Registration & Login: Sign up as a regular user or a team lead. Team leads have extra privileges like creating teams and assigning tasks.
Team Management: Team leads can create teams and add registered users as members.
Project & Task Tracking: Create projects within teams and add tasks with statuses like "To Do," "In Progress," or "Done." Team leads can assign tasks to members.
Comments: Users can comment on tasks to discuss progress or share updates.
Role-Based Access: Regular users can view teams and tasks, while team leads can create, edit, or delete them.
Responsive UI: The interface is clean and works decently on both desktop and mobile (still polishing the mobile part!).
🛠️ Tech Stack
Backend
Java 17: Core language for the server.
Spring Boot: Powers the API, security, and database interactions.
Spring Security: Handles authentication using JWT tokens.
PostgreSQL: Stores all data, including users, teams, and tasks.
Maven: Manages dependencies and builds.
Frontend
React: Drives the interactive user interface.
TypeScript: Adds type safety to JavaScript for better debugging.
Tailwind CSS: Simplifies styling with utility-first classes.
React Router: Manages navigation between pages.
Vite: A fast build tool for the frontend.
🚀 How to Run It
I tested this on my Windows 11 laptop, but it should work on Mac or Linux too. You’ll need Java, Node.js, and PostgreSQL installed.

Prerequisites
Java 17 (I used OpenJDK)
Node.js 18 or later
PostgreSQL 15
Maven
npm (included with Node.js)
Backend Setup
Clone the repository:
text


git clone https://github.com/your-username/tasksync-pms.git
cd tasksync-pms

Set up PostgreSQL:
Create a database named tasksync.
Update src/main/resources/application.properties with your database credentials:



spring.datasource.url=jdbc:postgresql://localhost:5432/tasksync
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
Build and run the backend:




mvn clean install
mvn spring-boot:run
The server will start at http://localhost:8080.
Frontend Setup
Open a new terminal and navigate to the frontend folder:



cd frontend
Install dependencies:


Copy
npm install
Run the frontend:
text


npm run dev
The app will be available at http://localhost:8081.



Using the App
Visit http://localhost:8081/register to sign up.
Choose TEAM_LEAD to create teams or USER to join them.
Log in to access the teams page. Team leads can create teams and add members.
Click a team to view its projects, then a project to see tasks. Team leads can update tasks, and all users can add comments.
🗄️ Database Schema
The app uses the following tables:

users: Stores user details (id, first_name, last_name, email, password, role).
teams: Stores team names and IDs.
team_members: Links users to teams (many-to-many relationship).
projects: Stores project names and links to teams.
tasks: Stores tasks with titles, statuses, and assignees.
comments: Stores task comments.
tokens: Stores JWT access and refresh tokens.
⚠️ Known Issues
The team list might not load immediately after login. A page refresh usually fixes it.
Mobile responsiveness is a work in progress.
Error messages could be more user-friendly.
🔮 Future Ideas
Add a search feature for finding users when adding team members.
Allow team leads to remove members from teams.
Implement notifications for task updates.
Improve the UI with better colors, animations, and mobile support.
💡 Why I Built This
I wanted to challenge myself to build a full-stack app with real-world features like authentication and role-based permissions. Learning JWT tokens and React context was tricky, but it was worth it! My teacher asked us to create something practical, and I thought a project management tool would be perfect for group work.

🤝 Contributing
Since this is my school project, I’m not accepting contributions yet. But if you spot bugs or have ideas, feel free to open an issue on GitHub! I might open it up for collaboration after I get my grade.

📜 License
This is a school project, so there’s no formal license yet. Please don’t copy it for your own homework—that’s not cool! 😅

Thanks for checking out TaskSync PMS! I hope you like it as much as I enjoyed building it! If you have feedback, drop it in the issues section. 😊

<p align="center"> <a href="https://github.com/your-username/tasksync-pms/issues">Report a Bug</a> | <a href="https://github.com/your-username/tasksync-pms">View on GitHub</a> </p>
