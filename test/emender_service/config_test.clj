(ns emender-service.config-test
  (:require [clojure.test :refer :all]
            [emender-service.config :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))

