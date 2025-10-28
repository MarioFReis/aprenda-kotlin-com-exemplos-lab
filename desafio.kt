// Desafio de projeto
// Abstraindo Formações da DIO Usando Orientação a Objetos com Kotlin
// 28/10/20025

//Classe Nivel do Usuario 
enum class Nivel {
    JUNIOR,
    PLENO,
    SENIOR
}

//Classe Usuario
class Usuario(val nome: String, val nivel: Nivel)

fun cadastrarUsuario(nome: String?, nivelStr: String?): Usuario? {
   
    if (nome.isNullOrBlank()) {
        println("Erro: Nome do usuário não pode ser nulo ou vazio.")
        return null
    }

    // Tenta converter a string para um valor do enum
    val nivel = try {
        Nivel.valueOf(nivelStr?.uppercase() ?: "")
    } catch (e: IllegalArgumentException) {
        println("Erro: Nível '$nivelStr' inválido. Use Junior, Senior ou Pleno.")
        return null
    }

    return Usuario(nome, nivel)
}


// Classe ConteudoEducacional
data class ConteudoEducacional(var nome: String, val duracao: Int =1)

// Função para cadastrar conteúdo educacional
fun cadastrarConteudoEducacional(nome: String?, duracao: Int): ConteudoEducacional? {
    if (nome.isNullOrBlank()) {
        println("Erro: Nome do conteúdo não pode ser nulo ou vazio.")
        return null
    }

    if (duracao <= 0) {
        println("Erro: A duração deve ser maior que 0.")
        return null
    }

    return ConteudoEducacional(nome, duracao)
}

// Classe Formacao
data class Formacao(val nome: String, var conteudos: List<ConteudoEducacional>)

// Função para cadastrar formação
fun cadastrarFormacao(nome: String?, conteudos: List<ConteudoEducacional>?): Formacao? {
    if (nome.isNullOrBlank()) {
        println("Erro: Nome da formação não pode ser nulo ou vazio.")
        return null
    }
    if (conteudos.isNullOrEmpty()) {
        println("Erro: A formação deve ter pelo menos um conteúdo.")
        return null
    }
    return Formacao(nome, conteudos)
}

// Função para matricular usuário em uma formação
fun matricular(usuario: Usuario?, formacao: Formacao?, mapaMatriculas: MutableMap<Formacao, MutableList<Usuario>>) {
    if (usuario == null || formacao == null) {
        println("Erro: Usuário ou formação inválidos para matrícula.")
        return
    }
    mapaMatriculas.computeIfAbsent(formacao) { mutableListOf() }.add(usuario)
    println("Usuário '${usuario.nome}' matriculado na formação '${formacao.nome}'.")
}

// Função para apresentar usuários inscritos em uma formação
fun apresentarInscritos(formacao: Formacao, mapaMatriculas: Map<Formacao, List<Usuario>>) {
    println("\nUsuários inscritos na formação '${formacao.nome}':")
    val inscritos = mapaMatriculas[formacao]
    if (inscritos.isNullOrEmpty()) {
        println("Nenhum usuário matriculado.")
    } else {
        inscritos.forEach { println("- ${it.nome} (${it.nivel})") }
    }
}


fun main() {
    
    //cadastrando usuarios 
    val u1 = cadastrarUsuario("Mario", "Junior")
    val u2 = cadastrarUsuario("Joao", "") // inválido
    val u3 = cadastrarUsuario("", "") // inválido
    val u4 = cadastrarUsuario("Ana", "Pleno")
    val u5 = cadastrarUsuario("Dudu", "Senior")
   
    //cadastrando conteudos 
    val c1 = cadastrarConteudoEducacional("Kotlin Básico", 2)
    val c2 = cadastrarConteudoEducacional("", 3) // inválido
    val c3 = cadastrarConteudoEducacional("Java Avançado", 0) // inválido
    val c4 = cadastrarConteudoEducacional("Banco de Dados", 5)

    
  // Cadastro de formação
   val formacao = cadastrarFormacao("Formação Desenvolvedor", listOfNotNull(c1, c2, c3, c4))

   
 // Mapa para controlar matrículas
    val mapaMatriculas = mutableMapOf<Formacao, MutableList<Usuario>>()

    // Matricular usuários
    matricular(u1, formacao, mapaMatriculas)
    matricular(u2, formacao, mapaMatriculas)// inválido
    matricular(u3, formacao, mapaMatriculas)// inválido
    matricular(u4, formacao, mapaMatriculas)
    matricular(u5, formacao, mapaMatriculas)

    // Apresentar inscritos
    if (formacao != null) {
        apresentarInscritos(formacao, mapaMatriculas)
    }
}
