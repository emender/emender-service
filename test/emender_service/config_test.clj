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

(deftest test-get-api-prefix-existence
    "Check that the emender-service.config/get-api-prefix definition exists."
    (testing "if the emender-service.config/get-api-prefix definition exists."
        (is (callable? 'emender-service.config/get-api-prefix))))

(deftest test-path-to-emender-existence
    "Check that the emender-service.config/path-to-emender definition exists."
    (testing "if the emender-service.config/path-to-emender definition exists."
        (is (callable? 'emender-service.config/path-to-emender))))

(deftest test-path-to-tests-existence
    "Check that the emender-service.config/path-to-tests definition exists."
    (testing "if the emender-service.config/path-to-tests definition exists."
        (is (callable? 'emender-service.config/path-to-tests))))

(deftest test-verbose?-existence
    "Check that the emender-service.config/verbose? definition exists."
    (testing "if the emender-service.config/verbose? definition exists."
        (is (callable? 'emender-service.config/verbose?))))

(deftest test-use-result-cache?-existence
    "Check that the emender-service.config/use-result-cache? definition exists."
    (testing "if the emender-service.config/use-result-cache? definition exists."
        (is (callable? 'emender-service.config/use-result-cache?))))

(deftest test-pretty-print-edn?-existence
    "Check that the emender-service.config/pretty-print-edn? definition exists."
    (testing "if the emender-service.config/pretty-print-edn? definition exists."
        (is (callable? 'emender-service.config/pretty-print-edn?))))

(deftest test-delete-workdirs?-existence
    "Check that the emender-service.config/delete-workdirs? definition exists."
    (testing "if the emender-service.config/delete-workdirs? definition exists."
        (is (callable? 'emender-service.config/delete-workdirs?))))

