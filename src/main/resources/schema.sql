-- Enable LTree
-- Execute this with in tmdb and using superuser access
CREATE EXTENSION IF NOT EXISTS LTREE WITH SCHEMA public;

-- Create table
CREATE TABLE IF NOT EXISTS facts (
  path ltree PRIMARY KEY,
  boost int,
  rules jsonb
);

-- Create index
CREATE INDEX IF NOT EXISTS facts_path_idx ON facts USING gist (path);