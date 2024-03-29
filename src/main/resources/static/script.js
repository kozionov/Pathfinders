async function fetchBookById(id) {
  const response = await fetch("/api/books/" + id);
  const book = await response.json();
  return book;
} 

async function fetchAllAuthors() {
  const authorsResponse = await fetch("/api/authors");
  const authors = await authorsResponse.json();
  return authors;
}

async function fetchAllGenres() {
  const genresResponse = await fetch("/api/genres");
  const genres = await genresResponse.json();
  return genres;
}

async function fetchUserById(id) {
  const response = await fetch("/api/users/" + id);
  const user = await response.json();
  return user;
}

async function fetchAllRoles() {
  const rolesResponse = await fetch("/api/roles");
  const roles = await rolesResponse.json();
  return roles;
}

async function fetchAllDirectors() {
  const directorsResponse = await fetch("/api/directors");
  const directors = await directorsResponse.json();
  return directors;
}

async function fetchAllScores() {
  const scoresResponse = await fetch("/api/scores");
  const scores = await scoresResponse.json();
  return scores;
}

async function fetchClubByUserId(id) {
  const clubResponse = await fetch("/api/clubs/user/" + id);
  const club = await clubResponse.json();
  return club;
}

async function fetchRecordsByLogId(id) {
  const recordsResponse = await fetch("/api/records/log/" + id);
  const records = await recordsResponse.json();
  return records;
}

async function fetchUsersByLogId(id) {
  const usersResponse = await fetch("/api/users/log/" + id);
  const users = await usersResponse.json();
  return users;
}

async function fetchLogsByClubId(id) {
  const logsResponse = await fetch("/api/logs/" + id);
  const logs = await logsResponse.json();
  return logs;
}