# Oreo

This is the Oreo chatbot project. Given below are instructions on how to use it.

## Running Oreo

From the project folder, run:

```powershell
.\gradlew.bat run
```

Oreo opens as a chat window. Enter commands such as `todo buy milk`, `list`,
`mark 1`, `find milk`, and `bye` in the input box; press Enter or select **Send**.
Tasks are saved immediately to `data/Oreo.txt` after each change.

## Tags

Add one or more tags to any new task by placing them at the end of the command:

```text
todo watch a movie #fun #weekend
deadline submit report /by 2026-09-30 #school
event team meeting /from 2026-09-15 /to 2026-09-16 #project
```

Tags must start with `#` and can contain letters, numbers, hyphens, and underscores.
Use `tag 1 #important` or `untag 1 #fun` to update an existing task. Use
`list tags` to group the current tasks by tag.

## Setting up in Intellij

Prerequisites: JDK 25, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 25** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/Oreo.java` file, right-click it, and choose `Run Oreo.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:
   ```
    ____        _        
   |  _ \ _   _| | _____ 
   | | | | | | | |/ / _ \
   | |_| | |_| |   <  __/
   |____/ \__,_|_|\_\___|
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.
