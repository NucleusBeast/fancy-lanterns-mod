#!/usr/bin/env bash

find . -type f -name "*.png" -print0 | while IFS= read -r -d '' image; do
  cat > "${image}.mcmeta" <<'EOF'
{
  "animation": {
    "frametime": 8
  }
}
EOF

  echo "Ustvarjen: ${image}.mcmeta"
done