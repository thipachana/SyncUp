#!/bin/bash

BASE_URL="http://localhost:8080"
COOKIE_FILE="$(mktemp)"
BODY_FILE="$(mktemp)"
EMAIL="smoketest$(date +%s)@syncup.local"
PASSWORT="Test1234"

trap 'rm -f "$COOKIE_FILE" "$BODY_FILE"' EXIT

check() {
    NAME="$1"
    EXPECTED="$2"
    ACTUAL="$3"

    if [ "$ACTUAL" = "$EXPECTED" ]; then
        echo "✅ $NAME ($ACTUAL)"
    else
        echo "❌ $NAME - erwartet $EXPECTED, erhalten $ACTUAL"
        cat "$BODY_FILE"
        echo
        exit 1
    fi
}

echo "=== SyncUp Backend Smoke Test ==="
echo

STATUS=$(curl -sS -o "$BODY_FILE" -w "%{http_code}" \
    -X POST "$BASE_URL/api/auth/register" \
    -H "Content-Type: application/json" \
    -d "{\"name\":\"Smoke Test\",\"email\":\"$EMAIL\",\"passwort\":\"$PASSWORT\"}")

check "Registrierung" "201" "$STATUS"

STATUS=$(curl -sS -o "$BODY_FILE" -w "%{http_code}" \
    -c "$COOKIE_FILE" \
    -X POST "$BASE_URL/api/auth/login" \
    -H "Content-Type: application/json" \
    -d "{\"email\":\"$EMAIL\",\"passwort\":\"$PASSWORT\"}")

check "Login" "200" "$STATUS"

STATUS=$(curl -sS -o "$BODY_FILE" -w "%{http_code}" \
    -b "$COOKIE_FILE" \
    "$BASE_URL/api/auth/me")

check "Session /me" "200" "$STATUS"

STATUS=$(curl -sS -o "$BODY_FILE" -w "%{http_code}" \
    -b "$COOKIE_FILE" \
    "$BASE_URL/api/benutzer")

check "Benutzerliste" "200" "$STATUS"

STATUS=$(curl -sS -o "$BODY_FILE" -w "%{http_code}" \
    -b "$COOKIE_FILE" \
    -X POST "$BASE_URL/api/me/termine" \
    -H "Content-Type: application/json" \
    -d '{
        "titel":"Smoke-Test-Termin",
        "beschreibung":"Automatischer Backend-Test",
        "datum":"2026-09-24",
        "startzeit":"10:00",
        "endzeit":"11:00"
    }')

check "Persönlichen Termin erstellen" "201" "$STATUS"

TERMIN_ID=$(python3 -c 'import json,sys; print(json.load(open(sys.argv[1]))["terminId"])' "$BODY_FILE")

STATUS=$(curl -sS -o "$BODY_FILE" -w "%{http_code}" \
    -b "$COOKIE_FILE" \
    "$BASE_URL/api/me/termine")

check "Persönliche Termine laden" "200" "$STATUS"

STATUS=$(curl -sS -o "$BODY_FILE" -w "%{http_code}" \
    -b "$COOKIE_FILE" \
    -X DELETE "$BASE_URL/api/me/termine/$TERMIN_ID")

check "Persönlichen Termin löschen" "204" "$STATUS"

STATUS=$(curl -sS -o "$BODY_FILE" -w "%{http_code}" \
    -b "$COOKIE_FILE" \
    -X POST "$BASE_URL/api/auth/logout")

check "Logout" "200" "$STATUS"

STATUS=$(curl -sS -o "$BODY_FILE" -w "%{http_code}" \
    -b "$COOKIE_FILE" \
    "$BASE_URL/api/auth/me")

check "Nach Logout kein Zugriff" "401" "$STATUS"

STATUS=$(curl -sS -o "$BODY_FILE" -w "%{http_code}" \
    "$BASE_URL/api/termine")

check "/api/termine ohne Login geschützt" "401" "$STATUS"

echo
echo "✅ ALLE BACKEND-SMOKE-TESTS ERFOLGREICH"
