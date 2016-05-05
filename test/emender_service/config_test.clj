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

;
; Tests for various functions
;

(deftest test-parse-int-existence
    "Check that the emender-service.config/parse-int definition exists."
    (testing "if the emender-service.config/parse-int definition exists."
        (is (callable? 'emender-service.config/parse-int))))

(deftest test-parse-float-existence
    "Check that the emender-service.config/parse-float definition exists."
    (testing "if the emender-service.config/parse-float definition exists."
        (is (callable? 'emender-service.config/parse-float))))

(deftest test-parse-boolean-existence
    "Check that the emender-service.config/parse-boolean definition exists."
    (testing "if the emender-service.config/parse-boolean definition exists."
        (is (callable? 'emender-service.config/parse-boolean))))

(deftest test-update-configuration-existence
    "Check that the emender-service.config/update-configuration definition exists."
    (testing "if the emender-service.config/update-configuration definition exists."
        (is (callable? 'emender-service.config/update-configuration))))

(deftest test-load-configuration-existence
    "Check that the emender-service.config/load-configuration definition exists."
    (testing "if the emender-service.config/load-configuration definition exists."
        (is (callable? 'emender-service.config/load-configuration))))

(deftest test-print-configuration-existence
    "Check that the emender-service.config/print-configuration definition exists."
    (testing "if the emender-service.config/print-configuration definition exists."
        (is (callable? 'emender-service.config/print-configuration))))

