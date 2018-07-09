def READ(val){
  return val
}

def EVAL(val){
  return val
}

def PRINT(val){
  return val
}

def REP(val){
  PRINT(EVAL(READ(val)))
}

while(true){
  def line = System.console().readLine 'user> '
  if (line == null) {
    break
  }
  println REP(line)
}
