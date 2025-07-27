# HTML Deepest Text Analyzer

Este projeto Java foi desenvolvido como parte de um desafio técnico de trabalho. O objetivo é acessar uma página HTML via URL e identificar **o texto mais profundo** dentro da hierarquia de tags HTML.

## 📌 Descrição

A aplicação lê o conteúdo HTML de uma página da web, analisa sua estrutura e retorna o **texto localizado na maior profundidade aninhada** das tags HTML. Caso o HTML esteja malformado (por exemplo, tags não balanceadas), o programa retorna uma mensagem de erro apropriada.

## 🚀 Como Executar

### Pré-requisitos

- Java JDK 8 ou superior instalado
- Acesso ao terminal (cmd, bash, etc.)

### Compilação

1. Salve o código em um arquivo chamado `HtmlAnalyzer.java`
2. Compile o arquivo com o seguinte comando no terminal:

```bash
javac HtmlAnalyzer.java
```

### Execução

Após compilar, execute passando a URL como argumento:

```bash
java HtmlAnalyzer <url>
```

Exemplo:

```bash
java HtmlAnalyzer https://www.exemplo.com
```

Se a página for carregada com sucesso e estiver bem formada, o terminal exibirá:

```
Deepest text: Aqui está o texto mais profundo
```

Caso ocorra algum erro, serão mostradas mensagens como:

- `Error: URL not provided.`
- `Error: Unable to read the page.`
- `Error: Unable to find the deepest text.`
- `Malformed HTML`

## 🧠 Lógica de Funcionamento

- A cada abertura de uma tag `<...>` o nível de profundidade é incrementado.
- A cada fechamento de tag `</...>` a profundidade é decrementada.
- O texto mais profundo é aquele encontrado no maior nível de profundidade durante a iteração pelo HTML.
- O programa ignora o conteúdo dentro de tags e só considera o texto visível.
- Em casos de HTML malformado (ex: fechamento incorreto de tags), o programa informa `"Malformed HTML"`.

## 📄 Exemplo de Uso

Se você tiver uma página HTML como:

```html
<html>
  <body>
    <div>
      <span>
        <p>Texto profundo</p>
      </span>
    </div>
  </body>
</html>
```

O resultado será:

```
Deepest text: Texto profundo
```

## 🧑‍💻 Autor

Desenvolvido por mim, Marcos Goulart como parte de um desafio técnico.
