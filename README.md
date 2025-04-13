# PBL
A Real time railway management system, with options to add/delete platforms and trains.  
A GUI will also be used for this, everything will be in JAVA.

# PostgreSQL Setup Instructions

## Step 1: Open Command Line

1. Navigate to the folder where `schema.sql` and `seed.sql` are located.

## Step 2: Open SQL Shell (psql)

1. Create a new database using the SQL shell:

   ```sql
   CREATE DATABASE your_database;
   ```

   > 🔴 **Important:** Replace `your_database` with the name of your actual database. This name will be used in the next steps.

## Step 3: Execute Schema File from Command Line

In the Command Line, run the following command to create the database schema:

```bash
psql -U your_username -d your_database -f schema.sql
```

- Replace `your_username` with your PostgreSQL username (usually `postgres`).
- Replace `your_database` with the name you created in Step 2.

## Step 4: Insert Dummy Data

To insert some dummy data into the database, run:

```bash
psql -U your_username -d your_database -f seed.sql
```

That’s it! Your database should now be created and populated with initial data.

---

## Troubleshooting: psql Command Not Recognized

If you get an error running the `psql` command in the Command Prompt, it may be because the PostgreSQL executable is not in your system's **Path**. Follow these steps to add the PostgreSQL `bin` directory to your **Path**:

1. **Find the PostgreSQL Bin Directory:**
   - The directory is typically located at:
     ```
     C:\Program Files\PostgreSQL\<version>\bin\
     ```
     Replace `<version>` with your installed PostgreSQL version.

2. **Edit the Environment Variable Path:**
   - Open the **Environment Variables** window:
      1. Right-click on "This PC" or "Computer" and select **Properties**.
      2. Click on **Advanced system settings**.
      3. In the System Properties window, click the **Environment Variables** button.
   - **Edit the Existing Path Variable:**
      1. In the "System variables" section, find and select the variable named `Path` (this may already contain paths for Python or other tools).
      2. Click **Edit**.
      3. Click **New** and add the full path to PostgreSQL’s `bin` directory (e.g., `C:\Program Files\PostgreSQL\<version>\bin\`).
      4. Click **OK** to save your changes.
   - **Restart Your Command Prompt:**  
     Close and reopen your command prompt to apply the changes.

3. **Verify the Installation:**
   - Open a new Command Prompt window and run:
     ```bash
     psql --version
     ```
   - This should display the installed PostgreSQL version.

By following these steps, the `psql` command should now be recognized in your command prompt.
Now follow from Step 3 again.
---

# Setting up Environment Variables

To ensure your application can securely connect to the database, you need to set up environment variables for the database URL, username, and password. This prevents sensitive information from being hardcoded in your application.

## Prerequisites

* A PostgreSQL database instance.
* The database URL, username, and password.

## Using the Environment Variables Editor on Windows:

1.  Open the Environment Variables editor. You can do this by searching for "Edit the system environment variables" in the Start menu.

2.  In the System Properties window, click the "Environment Variables..." button.

3.  In the Environment Variables window, in the "System variables" section, click "New...".

4.  Create three new system variables:

    * **Variable name:** `JDBC_DATABASE_URL`
        * **Variable value:** `your_database_url` (e.g., `jdbc:postgresql://localhost:5432/railway`)
    * **Variable name:** `JDBC_DATABASE_USER`
        * **Variable value:** `your_username` (e.g., `postgres`)
    * **Variable name:** `JDBC_DATABASE_PASSWORD`
        * **Variable value:** `your_password`

5.  Click "OK" to save each variable, and then click "OK" to close the remaining windows.