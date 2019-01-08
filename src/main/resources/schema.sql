-- Enable LTree
-- Execute this with in tmdb and using superuser access
CREATE EXTENSION IF NOT EXISTS LTREE WITH SCHEMA public;

-- Create table
CREATE TABLE IF NOT EXISTS tree (
  id serial PRIMARY KEY,
  letter CHAR,
  path ltree
);

-- Create index
CREATE INDEX IF NOT EXISTS tree_path_idx ON tree USING gist (path);