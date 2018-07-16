cd src
javac util/*.java -d ./../bin/


javac base/model/interfaces/*.java -d ./../bin/
javac base/model/*.java -d ./../bin/

javac servidor/model/*.java -d ./../bin/

javac cliente/interfaces/anotacoes/*.java -d ./../bin/
javac cliente/interfaces/*.java -d ./../bin/
javac cliente/controller/*.java -d ./../bin/
javac cliente/dao/*.java -d ./../bin/
javac cliente/view/*.java -d ./../bin/
javac cliente/*.java -d ./../bin/


echo "Compilação concluída."
