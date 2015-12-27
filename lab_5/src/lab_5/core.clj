(ns lab-5.core
  (:import [org.opencv.core Mat MatOfInt MatOfFloat CvType]
           [org.opencv.imgcodecs Imgcodecs]
           [org.opencv.imgproc Imgproc]))

(def src-file "/home/s/lab_5_src.jpg")
(def dst-file "/home/s/lab_5_result.png")

(defn read-img [path]
  (Imgcodecs/imread path Imgcodecs/CV_LOAD_IMAGE_GRAYSCALE))

;(def kernel
;  (doto (Mat. 3 3 CvType/CV_32F)
;    (.put 0 0 (double-array [-1]))
;    (.put 0 1 (double-array [0] ))
;    (.put 0 2 (double-array [1] ))
;    (.put 1 0 (double-array [-1]))
;    (.put 1 1 (double-array [0] ))
;    (.put 1 2 (double-array [1] ))
;    (.put 2 0 (double-array [-1]))
;    (.put 2 1 (double-array [0] ))

(def kernel
  (doto (Mat. 3 3 CvType/CV_32F)
    (.put 0 0 (double-array [-1]))
    (.put 0 1 (double-array [-1] ))
    (.put 0 2 (double-array [-1] ))
    (.put 1 0 (double-array [0]))
    (.put 1 1 (double-array [0] ))
    (.put 1 2 (double-array [0] ))
    (.put 2 0 (double-array [1]))
    (.put 2 1 (double-array [1] ))
    (.put 2 2 (double-array [1] ))))

(defn transform-image [src dst kernel]
  (Imgproc/filter2D src dst -1 kernel))

(defn -main
  "Do lab4:
  * read image
  * clone src to dst
  * apply max mask filter to every pixel
  * write dst to dst_file"
  [_]
  (let [src (Imgcodecs/imread src-file Imgcodecs/CV_LOAD_IMAGE_GRAYSCALE)
        dst (.clone src)]
    (doall (transform-image src dst kernel))
    (Imgcodecs/imwrite dst-file dst)))
