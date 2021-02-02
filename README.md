# SharePreferences

Um projeto demonstrando com salvar, carregar e apagar aquivos do share preferences.

O SharePreferences é uma classe, que quando estanciada cria um arquivo XML na propria pasta do aplicativo.
Neste arquivo são armazenados dados dos tipos primitivos (String, Integer, Float, Boolean), o estilo armazenamento é
de biblioteca (CHAVE VALOR).

Usado para guardar algumas preferencias de usúario ou uma base de dados muito pequena.

Sua manipulação é na thread principal e com a estanciada de SharePreferences.Editor().
