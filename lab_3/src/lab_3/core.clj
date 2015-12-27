(ns lab-3.core
  (:import [org.opencv.core Mat MatOfInt MatOfFloat]
           [org.opencv.imgcodecs Imgcodecs]
           [org.opencv.imgproc Imgproc]))

(def src-file "/home/s/lab_3_test.jpg")
(def dst-file "/home/s/lab_3_test_result.png")

(defn read-img [path]
  (Imgcodecs/imread path Imgcodecs/CV_LOAD_IMAGE_GRAYSCALE))

(defn transform-pixel [img col row]
  (let [submat (for [x [(dec col) col (inc col)]
                     y [(dec row) row (inc row)]]
                     (or (first (.get img x y)) 0))]
    (/ (reduce + (map * [1 2 1 2 4 2 1 2 1] submat)) 16)))

(defn transform-image [img]
  (for [row (range (.width img)) col (range (.height img))]
    (.put img col row (double-array [(transform-pixel img col row)]))))

(defn -main
  "Do lab3:
  * read image
  * clone src to dst
  * apply mask filter to every pixel
  * write dst to dst_file"
  [_]
  (let [src (Imgcodecs/imread src-file Imgcodecs/CV_LOAD_IMAGE_GRAYSCALE)
        dst (.clone src)]
    (doall (transform-image dst))
    (Imgcodecs/imwrite dst-file dst)))
