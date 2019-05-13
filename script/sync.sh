#!/usr/bin/env bash
rsync -a -e "ssh -p 3022" --exclude 'out' --exclude '.gradle' --exclude 'build' /home/issac/Workspace/Coding/CacheBenchmark hkucs@localhost:/home/hkucs/Migration