(ns emender-service.exec)

(defn exec
    "Execute external command and pass given number of parameters to it."
    ; command called without parameters
    ([command]
     (doto (. (Runtime/getRuntime) exec command)
           (.waitFor)
           (.destroy)))
    ; command called with one parameter
    ([command param1]
     (doto (. (Runtime/getRuntime) exec (str command " " param1))
           (.waitFor)
           (.destroy)))
    ; command called with two parameters
    ([command param1 param2]
     (doto (. (Runtime/getRuntime) exec (str command " " param1 " " param2))
           (.waitFor)
           (.destroy)))
    ; command called with three parameters
    ([command param1 param2 param3]
     (doto (. (Runtime/getRuntime) exec (str command " " param1 " " param2 " " param3))
           (.waitFor)
           (.destroy)))
    ; command called with four parameters
    ([command param1 param2 param3 param4]
     (doto (. (Runtime/getRuntime) exec (str command " " param1 " " param2 " " param3 " " param4))
           (.waitFor)
           (.destroy)))
    ; command called with five parameters
    ([command param1 param2 param3 param4 param5]
     (doto (. (Runtime/getRuntime) exec (str command " " param1 " " param2 " " param3 " " param4 " " param5))
           (.waitFor)
           (.destroy)))
    ; command called with six parameters
    ([command param1 param2 param3 param4 param5 param6]
     (doto (. (Runtime/getRuntime) exec (str command " " param1 " " param2 " " param3 " " param4 " " param5 " " param6))
           (.waitFor)
           (.destroy))))

