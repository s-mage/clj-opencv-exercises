(ns lab-1.core
  (:import [org.opencv.imgcodecs Imgcodecs]
           [org.opencv.imgproc Imgproc]))

(def src-file "/home/s/lab_1_src.jpg")
(def dst-file "/home/s/lab_1_result.png")

(defn read-img [path]
  (Imgcodecs/imread path Imgcodecs/CV_LOAD_IMAGE_GRAYSCALE))

(defn -main
  "Do lab1:
  * read image
  * clone src to dst
  * apply threshold function that mutate dst
  * write dst to dst_file"
  [& args]
  (let [src (Imgcodecs/imread src-file Imgcodecs/CV_LOAD_IMAGE_GRAYSCALE)
        dst (.clone src)]
    (Imgproc/threshold src dst 128 256 Imgproc/THRESH_TOZERO_INV)
    (Imgcodecs/imwrite dst-file dst)))
