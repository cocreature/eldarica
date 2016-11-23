#!/bin/bash
set -o nounset
set -o errexit
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "${DIR}/../"
ant binary-distribution
cp dist/lazabs.jar "${DIR}/../../eldarica-jar/"
