# PBL
A Real time railway management system, with options to add/delete platforms and trains.
A GUI will also be used for this, everything will be in JAVA.
# Setting Up the Database

This guide will help you create all tables using the schema file, check existing tables, set the search path to `public`, and configure environment variables for database credentials.

---

## 1️⃣ **Creating Tables from `schema.sql`**

To create all the tables in your PostgreSQL database using the provided `schema.sql` file:

### **Step 1: Open PostgreSQL Terminal**
If using a local database, open your PostgreSQL terminal:
```sh
psql -U postgres -d railway
```

### **Step 2: Execute the Schema File**
Run the following command to execute the schema file and create all tables:
```sh
\i /path/to/schema.sql
```
For Windows (use forward slashes `/`):
```sh
\i C:/Code/Real-Time-Railway-Management-System/schema.sql
```

---

## 2️⃣ **Checking Created Tables**
After running the schema file, verify the tables using:
```sh
\dt public.*
```
This will display all tables under the `public` schema:
```
         List of relations
 Schema |   Name    | Type  |  Owner
--------+-----------+-------+----------
 public | platforms | table | postgres
 public | stations  | table | postgres
 public | trains    | table | postgres
(3 rows)
```

If no tables are found, ensure that your search path is set correctly.

---

## 3️⃣ **Setting the Search Path to `public`**
By default, PostgreSQL searches tables in the `public` schema, but if needed, you can set it manually:

### **Check Current Search Path**
```sh
SHOW search_path;
```

### **Set Search Path to `public`** (Temporary for Current Session)
```sh
SET search_path TO public;
```

### **Set Search Path Permanently** (for All Sessions)
If you want to set the search path permanently, update `postgresql.conf` or use:
```sh
ALTER ROLE postgres SET search_path TO public;
```
Replace `postgres` with your actual database username.

---


## ✅ **Summary**
| Task | Command/Step |
|------|-------------|
| **Run Schema File** | `\i /path/to/schema.sql` |
| **Check Tables** | `\dt public.*` |
| **Set Search Path (Temporary)** | `SET search_path TO public;` |
| **Set Search Path (Permanent)** | `ALTER ROLE postgres SET search_path TO public;` |


