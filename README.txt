
Application Name : GeeksPad

Team Members:

	1. Kaliraj Kalimuthu ( Red ID: 819956118).
	2. Sreerag Sreenivasan ( Red ID: 820911878).

About GeeksPad

    Geekspad is an android application where users can network with other tech people who have interest in particular technology. 

Guidelines to use our application:

 1. sign up us new user
    enter all required details, first name, last name, email, password, country, state city.
    add skills before confirming the registration.(Adding skills is optional).
2. login with username password.
   username - email id
  password - password entered during registration.
3. Login Success  directs you to list view in home page with all the users in the app.
  Click on any users take you to their profile. 
4. Change the view using bottom navigation menu. It has below options in order
      1. profile
      2. list view
      3. map view
     4. chat view.

User Views:

Action bar contains sign out  and filter menu. Filter menu is enabled only for List view and Map view.

1. Profile View - User can view their profile and edit their personal information and Skills. As of now user can edit any information other than email and password. 
Updated changes reflect in the profile immediately.

2. List view  and Map View
   View all the users in the app through list view and map. 
List View - Click any user -> takes you to their profile -> view their full details with skills -> Option for chat and call

Map View - View the markers on map. Click on marker opens marker window with user basic information.
Again clicking on the marker window takes you to the user profile -> view their full details with skills -> Option for chat and call

It has filter menu option on action bar. Apply filter based on country, state, skills and proficiency level. 
Make sure - state can always be used in conjunction with country , but never alone. Similarly, proficiency can always be used in conjunction with skills. Try any combination with above rules applied.

3. Chat View
 List all the users on the app. You can click  on any users and initiate the chat directly.

Limitation:

1) When filters are applied if proficiency level is given for a skill set as a filter condition, it will look for exact proficiency level match only.Also if proficiency level is ones applied and brought back to zero for next filter condition it treat proficiency level zero as a filter parameter.

2) The selected item is not highlighted in add/update skill screen as we are using customised Recycler view adapters, the highting part looks way too complex.


  