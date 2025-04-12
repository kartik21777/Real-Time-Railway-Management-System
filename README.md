# PBL
A Real time railway management system, with options to add/delete platforms and trains.
A GUI will also be used for this, everything will be in JAVA.

# PostgreSQL Setup Instructions

## Step 1: Open Command Line as Administrator

1. Navigate to the folder where `schema.sql` and `seed.sql` are located.

## Step 2: Open SQL Shell (psql)

1. Create a new database using the SQL shell:

   ```sql
   CREATE DATABASE your_database;
   ```

   > 🔴 **Important:** Replace `your_database` with the name of your actual database. This name will be used in the next steps.

## Step 3: Execute Schema File from Command Line

In the Command Line (opened as Administrator), run the following command to create the database schema:

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

