-- Enable LTree
-- Execute this with in tmdb and using superuser access
-- CREATE EXTENSION IF NOT EXISTS LTREE WITH SCHEMA public;

-- Create table
create table IF NOT EXISTS tree(
  id serial primary key,
  letter char,
  path ltree
);

-- Create index
create index IF NOT EXISTS tree_path_idx on tree using gist (path);