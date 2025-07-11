#!/bin/bash

echo "Testing Player Compensation API endpoints"

# Test goalkeeper with clean sheets and saves
echo -e "\n\nTesting goalkeeper compensation:"
curl -X POST -H "Content-Type: application/json" -d '{
  "name": "Manuel Neuer",
  "position": "GOALKEEPER",
  "club": "Bayern Munich",
  "baseCompensation": 10000,
  "goalsScored": 0,
  "assists": 1,
  "cleanSheets": 10,
  "saves": 50,
  "tackles": 0,
  "passingAccuracy": 75,
  "minutesPlayed": 1800,
  "yellowCards": 1,
  "redCards": 0,
  "matchesPlayed": 20,
  "penaltiesSaved": 2,
  "penaltiesScored": 0,
  "bonusAmount": 0,
  "fineAmount": 0
}' http://localhost:8080/calculateCompensation | jq .

# Test defender with goals and clean sheets
echo -e "\n\nTesting defender compensation:"
curl -X POST -H "Content-Type: application/json" -d '{
  "name": "Virgil van Dijk",
  "position": "DEFENDER",
  "club": "Liverpool",
  "baseCompensation": 10000,
  "goalsScored": 3,
  "assists": 2,
  "cleanSheets": 8,
  "saves": 0,
  "tackles": 110,
  "passingAccuracy": 88,
  "minutesPlayed": 1750,
  "yellowCards": 3,
  "redCards": 0,
  "matchesPlayed": 20,
  "penaltiesSaved": 0,
  "penaltiesScored": 1,
  "bonusAmount": 0,
  "fineAmount": 0
}' http://localhost:8080/calculateCompensation | jq .

# Test midfielder with goals and assists
echo -e "\n\nTesting midfielder compensation:"
curl -X POST -H "Content-Type: application/json" -d '{
  "name": "Kevin De Bruyne",
  "position": "MIDFIELDER",
  "club": "Manchester City",
  "baseCompensation": 10000,
  "goalsScored": 8,
  "assists": 15,
  "cleanSheets": 0,
  "saves": 0,
  "tackles": 45,
  "passingAccuracy": 92,
  "minutesPlayed": 1650,
  "yellowCards": 2,
  "redCards": 0,
  "matchesPlayed": 19,
  "penaltiesSaved": 0,
  "penaltiesScored": 3,
  "bonusAmount": 0,
  "fineAmount": 0
}' http://localhost:8080/calculateCompensation | jq .

# Test forward with many goals
echo -e "\n\nTesting forward compensation:"
curl -X POST -H "Content-Type: application/json" -d '{
  "name": "Erling Haaland",
  "position": "FORWARD",
  "club": "Manchester City",
  "baseCompensation": 10000,
  "goalsScored": 25,
  "assists": 5,
  "cleanSheets": 0,
  "saves": 0,
  "tackles": 10,
  "passingAccuracy": 65,
  "minutesPlayed": 1700,
  "yellowCards": 4,
  "redCards": 1,
  "matchesPlayed": 20,
  "penaltiesSaved": 0,
  "penaltiesScored": 5,
  "bonusAmount": 0,
  "fineAmount": 0
}' http://localhost:8080/calculateCompensation | jq .

# Create a custom rule
echo -e "\n\nCreating a custom rule for a hat-trick bonus:"
curl -X POST -H "Content-Type: application/json" -d '{
  "position": "FORWARD",
  "description": "Hat-trick bonus for forwards",
  "ifcondition": "goalsScored >= 3 && matchesPlayed > 0 && (goalsScored / matchesPlayed) >= 3/20.0",
  "thencondition": "player.setBonusAmount(player.getBonusAmount() == null ? 300 : player.getBonusAmount() + 300); System.out.println(\"Applied hat-trick rate bonus for player: \" + player.getName());",
  "version": 1
}' http://localhost:8080/rule | jq .

# Test dynamic rule
echo -e "\n\nTesting dynamic rule application:"
curl -X POST -H "Content-Type: application/json" -d '{
  "name": "Erling Haaland",
  "position": "FORWARD",
  "club": "Manchester City",
  "baseCompensation": 10000,
  "goalsScored": 25,
  "assists": 5,
  "cleanSheets": 0,
  "saves": 0,
  "tackles": 10,
  "passingAccuracy": 65,
  "minutesPlayed": 1700,
  "yellowCards": 4,
  "redCards": 1,
  "matchesPlayed": 20,
  "penaltiesSaved": 0,
  "penaltiesScored": 5,
  "bonusAmount": 0,
  "fineAmount": 0
}' http://localhost:8080/calculateDynamicCompensation | jq .

echo -e "\n\nDone testing player compensation rules"
