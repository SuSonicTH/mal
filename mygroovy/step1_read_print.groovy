def READ(val){
	return new Reader(val).read()
}

def EVAL(val){
	return val
}

def PRINT(expression){
	return printExpression(expression)
}

def REP(val){
	PRINT(EVAL(READ(val)))
}

while(true){
	def line = System.console().readLine 'user> '
	if (line == null) {
		break
  	}
	try {
        println REP(line)
    } catch(exception) {
        println "Error: ${exception.message}"
    }
}

def printExpression(expression){
	if (expression instanceof List){
		def list = expression.collect { printExpression(it) }
		return ("(${list.join(" ")})")
	}else{
		return expression.toString()
	}
}

class Reader{
	def tokens = []
	def position = 0

	def Reader(String string){
		def matcher = string =~ /[\s,]*(~@|[\[\]{}()'`~^@]|"(?:\\.|[^\\"])*"|;.*|[^\s\[\]{}('"`,;)]*)/
		while (matcher.find()) {
			String token = matcher.group(1)
			if (token != null && !(token == "") && !(token[0] == ';')) {
				tokens.add(token)
			}
		}
	}

	def read(){
		 def token = peek()
		 switch (token) {
		 	case '(':
		 		return readList()
		 	default:
		 		return readAtom();
		 }
	}

	private String peek() {
		if (position >= tokens.size) {
			return null
		} else {
			return tokens[position]
		}
	}

	private String next() {
		if (position >= tokens.size) {
			return null
		} else {
			return tokens[position++]
		}
	}

	private readList(){
		def list=[]
		def token = next()
		while ((token = peek()) != null && token.charAt(0) != ')') {
			list<<read()
		}
		if (token!=')'){
			throw new Exception ("Expected ')' while reaching EOF")
		}
		next()
		return list
	}

	private readAtom(){
		def token = next()
		if (token=~/(^-?[0-9]+$)/){
			return Integer.parseInt(token)
		}else{
			return token
		}
	}
}
