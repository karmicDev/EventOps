CREATE TABLE Event (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL CHECK(length(trim(name)) > 0),
    starts_at TEXT NOT NULL CHECK(starts_at IS date(starts_at))
);