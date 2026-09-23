#!/usr/bin/env bash
set -euo pipefail
# Uses src/test/resources/application.properties (isolated H2); no demo users
# or application data are written to localhost:8080.
project_dir="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$project_dir/backend"
exec ./mvnw -Dtest=ApiIntegrationTests test
