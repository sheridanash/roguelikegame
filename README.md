# Capstone: Roguelike

## Problem Statement
Looking to kill 10 minutes? Play our game! Roguelikes are a popular genre of game where the game resets each time you die. Our game is an adventure game based on four elements: air, water, earth, and fire. Solve the puzzles, collect the treasure, and beat the boss to win!

Make an account and login to save your score, or play as a guest. If you get stuck, save your progress and come back later.


## Proposal

Simply open the website and play the game. Or if you are a returning user, log in and continue where you left off. When you win, your highest score will be listed in high scores under your username and be publicly available on our scores page!

### Scenario 1

Frogger_manXD starts the game and gets two of the four elements, then has to dash to his local frogger tournament. He remembered to save the game, so he can simply close the window, then log in later to continue right where he left off.

### Scenario 2

Guest23 did not feel like creating an account. However, they are very talented at games and beat the game with a score high enough to make the top 5 on the leaderboard. To make an account or not to make an account, that is the question…

### Game Rules

Objective: Collect all elements, defeat monsters and collect the ultimate treasure

How Elements Work: 
    Air - Allows hero push monster back three spaces back
    Water - Allows hero to walk on water
    Earth - Allows hero to pick up rocks and throw at monster to defeat
    Fire - Allows hero to burn walls

How Scoring Works: 
    Defeating Monster - 50 points 
    Collecting Element - 100 points
    Collecting Ultimate Treasure - 200 points 
    
Lives: 
    Get 100 Gold - Get another life 
    Lose Live When Hero Runs Into Monster

What to Avoid: 
    Monsters
    Water (if you do not have the water element)




## Vocabulary

<dl>
<dt>Player</dt>
<dd>Anyone who signs up for our game is a player. Otherwise, they are considered a guest.</dd>
<dt>Save</dt>
<dd>Saving the game will store your progress and attach it to the player account. </dd>
<dt>Points, Score, and High Score</dt>
<dd>Doing things in the game will reward the player with points. The number of points at the end of the game, win or lose, is the score. If it is the player's account-wide highest score, then that is the high score. </dd>
<dt>Leaderboard</dt>
<dd>Each player’s highest score will be listed here. </dd>
<dt>Game</dt>
<dd>Multiple maps with different layouts. </dd>
<dt>Map</dt>
<dd>Different layouts to find elements and treasures. </dd>
<dt>Hero</dt>
<dd>Controlled by player </dd>
<dt>Monster</dt>
<dd>Defends elements from hero. </dd>
<dt>Elements</dt>
<dd>Each element has their own power. </dd>
<dd>Fire: Allows hero to burn walls. </dd>
<dd>Water: Allows hero to walk on water. </dd>
<dd>Earth: Allows hero to pick up rocks and defeat monsters. </dd> 
<dd>Air: Allows hero to push monsters back three tiles. </dd>
</dl>



## Technical Requirements

1. Relational database
2. Spring Boot, MVC, Jpa Repository, Testing
3. React UI
4. Sensible layering and pattern choices
5. A full test suite that covers the domain and data layers
6. Deployment to AWS using EC2 and RDS.

### Security Requirements

Roguelike has three formal roles: GUEST, PLAYER and ADMIN. 

Anyone can play the game as a guest. Guests who create an account will become a player, then they can save their progress and register their high score to the leaderboard.

In effect, there are four levels of authorization. 

Actions that are available to:
- everyone (public)
- any authenticated user (anyone with a login)
- the PLAYER role
- the ADMIN role

## High-level Requirements

- Play the game (public).
- View the leaderboard (public).
- Register (public).
- Log in (public)
- Log out (authenticated)
- Save and continue the game (authenticated).
- Register to the leaderboard (authenticated).
- Change username (PLAYER).
- Change password (PLAYER).
- Reset any password (ADMIN).
- Delete entries on the leaderboard (ADMIN).

## Scenarios

### Register
Create an account button on the home page login portal.
Username must not be taken.
Enter password twice - they must match.


### Login
Login on home page - must log in or continue as GUEST before playing the game.


### Logout
PLAYERs who hit the logout button will be prompted to save the game first. 
For GUESTs, the create account button will exist where the logout button is.


### Play Game

After login (or continuing as a guest), GUESTs, PLAYERs, and ADMINs alike can all play the game. 

Only authenticated users can log out or see the save game option. 


### View Leaderboard

Anyone can view the leaderboard.

Only an authenticated ADMIN can see the delete entry button.


### View Profile

Authenticated PLAYERs can view their profile page to change their username or password. 
**Change Username**: Click the change button, type the new username, hit save.
**Change Password**: Click the change button, enter the old password, enter the new password twice, hit save.


## Reset Passwords (DEPRECIATED - feature currently makes no sense) 

An authenticated ADMIN can view all profiles and select the reset password button, for whatever reason (if a password is lost or forgotten). And the next time the user logs in they will be prompted to reset their password.


## Tips and Hints

## Technical Learning Goal Candidates

- How exactly will save states work? Currently do a massive store into MySQL DB for info about each relevant object in the game (coordinates of the player, elements unlocked, tile info, etc…)


## Deployment

- use RDS for persistent data storage
- use EC2 for the API
- use EC2 for the front-end application


## Approach

Planning is absolutely essential for a project this large. Create a complete list of concrete tasks required to finish. Tasks should take no longer than 4 hours. Schedule each task for a specific day. At the end of each day, take stock. Are you ahead of schedule, on schedule, or behind? If ahead, how can you put the extra time to use? 

If behind, what do you have to adjust to complete the project? Don't just hope that things will improve. Take concrete steps to simplify or remove features.

Test as you go. If you wait to test until the end, three things happen:
1. Your tests aren't as good.
2. The last bit of development becomes a slog. No one loves 100% testing. Spread the testing out over several days.
3. You gain false confidence because you don't see domain failures that are prevented in the UI. Remember, if we throw away the UI, the domain should still prevent all invalid transactions.


### The "Spec"

This document has the appearance of a formal specification, but it is not. There are too many missing pieces. It's up to you to complete the specification. When things aren't clear, ask clarifying questions. Your classmates and instructors are invaluable resources. Never make assumptions.

If you can't resolve an ambiguous requirement, ask your instructor for feedback.

## Deliverables

1. A schedule of concrete tasks (at most 4 hours per task) that represent all work required to finish your project along with task statuses
2. Diagrams: database schema, class, layer, flow
3. Wireframes: roughly sketch your UI and how one view transitions to another. You can also use design tools to create wireframes.
4. A short presentation, 4 to 6 slides, describing who you are, how you found programming, and your project
5. Complete project source code free of compiler errors
6. A schema creation script along with any DML for data needed to run the application (security roles, default data, etc)
7. If it isn't straight-forward, instruction to set up and run your application
8. A complete test suite with all tests passing

