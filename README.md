# Discord LFG Bot

A Discord bot that organizes “Looking For Group” (LFG) posts.

---

## Features

- Watches specified channel for posts containing **!lfg**
- Creates message emebed with configurable color to display LFG posts.
- Slash command integration (Currently for command to display link to R6 Tracker with specified username)
---

## Prerequisites

- Java 21 (`java -version`)
- Discord account with access to the Discord Developer Portal
- Permission to invite a bot to the target server (Manage Server)

---

## Create the Application and Bot (Discord Developer Portal)

1. Open the **Discord Developer Portal** → **Applications** → **New Application** → name it → **Create**.
2. In the sidebar, open **Bot** → **Add Bot** → **Yes, do it!**
3. Under **Build-A-Bot**, copy the **Token** (you will place this in `.env` as `TOKEN=`).
4. **Privileged Gateway Intents** (still on the **Bot** tab):
    - Enable **MESSAGE CONTENT INTENT** if the bot reads message text.
    - Enable **SERVER MEMBERS INTENT** if the bot inspects server members.
    - Click **Save Changes**.
5. **OAuth2 → URL Generator**:
    - **Scopes**: check `bot` and `applications.commands`.
    - **Bot Permissions** (minimum):
        - View Channels
        - Send Messages
        - Manage Messages 
        - Embed Links
        - Read Message History
        - Use Slash Commands
    - Copy the generated URL and open it to invite the bot to your server.

---

## Environment Configuration (`.env`)

Create a plain-text file named `.env` at the project root.

- **macOS**: Files beginning with `.` are hidden. In your editor’s Save dialog, name it exactly `.env`. If your editor tries to add `.txt`, choose “All Files.”
- **Windows**: In Notepad, choose **Save as type:** *All Files*, then name the file `.env`.

**Minimal template (blank values):**
````
TOKEN=
LFG_CHANNEL=            # Channel IDs seperated by commas only
COLOR_AS_HEX=
GUILD_ID=
````