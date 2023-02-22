# Tafl Games

## Istruzioni per l'uso

- Creare una cartella vuota dove clonare il repository.
- Assicurarsi che sul proprio PC ci si sia registrati come autori con il proprio nome e la propria email. Per vedere la configurazione attuale usare:

        git config --global user.name
    
        git config --global user.email

    Se ci fossero cambiamenti da fare, riscrivere i comandi precedenti aggiungendo a fianco del primo il vostro vero nome e a fianco del secondo la vostra email istituzionale.
- Usare una shell bash per eseguire il comando:

        ./gradlew

    Questo permetterà di installare localmente l'ultima versione del wrapper per il nostro progetto.
- _[Opzionale]_ Verificare che il file `settings.gradle.kts` abbia questo contenuto:

        rootProject.name = "tafl-games"

- Eseguire un test con gradle usando:

        gradle build

    e appurare che il daemon parte come dovrebbe, passando il test. Ho scritto soltanto una classe Vector fake che non dovrebbe produrre errori.
- A titolo informativo se non l'avete mai provato, potete generare la javadoc con il comando: 

        gradle javadoc

    A quel punto nella cartella build ci sarà una sezione docs dove troverete alcuni files .html che mostreranno la javadoc prodotta a partire dai vostri commenti. Curatela molto fin da subito :)
    
- Questo repository è stato inizializzato a partire dal progetto di esempio creato dai professori, visibile al link https://github.com/unibo-oop/sample-gradle-project.git.
