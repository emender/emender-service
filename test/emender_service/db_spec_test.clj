(ns emender-service.db-spec-test
  (:require [clojure.test :refer :all]
            [emender-service.db-spec :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))

