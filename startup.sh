#!/bin/bash

function buildProjects() {
    echo "Building projects..."
    if [ "$CLEAN" = "true" ]; then
        echo "Running gradle clean"
        ./gradlew clean
    fi
    ./gradlew assemble
}

function composeDown() {
    echo "Bringing Docker Compose environment down..."
    docker compose down
}

function composeUp() {
    echo "Bringing Docker Compose environment up..."
    docker compose up -d
}

function removeImages() {
    echo "Removing Docker images..."
    docker image rm sb-strava-app -f
    docker image rm sb-strava-db -f
    docker volume rm sb-strava_db_data
}

function displayHelp() {
    echo "Usage: $0 [options]"
    echo "Options:"
    echo "  -c, --clean   Perform a gradle clean before building"
    echo "  -r, --remove  Remove Docker images before building"
    echo "  -h, --help    Display this help message"
    echo "  --no-start  Don't start the docker compose"
}

function __run__() {
    local CLEAN="false"
    local REMOVE_IMAGES="false"
    local START_CONTAINER="true"

    while [[ $# -gt 0 ]]; do
        key="$1"
        case "$key" in
        -c | --clean)
            CLEAN="true"
            shift
            ;;
        -r | --remove)
            REMOVE_IMAGES="true"
            shift
            ;;
        -h | --help)
            displayHelp
            return 0
            ;;
        --no-start)
            START_CONTAINER="false"
            shift
            ;;
        -? | --*) # Handle unknown options
            echo "Unknown option: $key" >&2
            displayHelp
            return 1
            ;;
        *)
            DOCKER_COMPOSE_ARGS+=("$1")
            shift
            ;;
        esac
    done

    composeDown
    if [ "$REMOVE_IMAGES" = "true" ]; then
        removeImages
    fi
    buildProjects
    if [ "$START_CONTAINER" = "true" ]; then
      composeUp "${DOCKER_COMPOSE_ARGS[@]}"
    fi
}

__run__ "$@"
