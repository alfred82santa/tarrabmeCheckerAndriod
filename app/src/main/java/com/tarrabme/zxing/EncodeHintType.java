/*
 * Copyright 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tarrabme.zxing;

/**
 * These are a set of hints that you may pass to Writers to specify their behavior.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public enum EncodeHintType {

  /**
   * Specifies what degree of error correction to use, for example in QR Codes.
   * Type depends on the encoder. For example for QR codes it's type
   * {@link com.google.zxing.qrcode.decoder.ErrorCorrectionLevel ErrorCorrectionLevel}.
   * For Aztec it is of type {@link Integer}, representing the minimal percentage of error correction words. 
   * Note: an Aztec symbol should have a minimum of 25% EC words.
   */
  ERROR_CORRECTION,

  /**
   * Specifies what character encoding to use where applicable (type {@link String})
   */
  CHARACTER_SET,

  /**
   * Specifies the matrix shape for Data Matrix (type {@link com.google.zxing.datamatrix.encoder.SymbolShapeHint})
   */
  DATA_MATRIX_SHAPE,

  /**
   * Specifies a minimum barcode size (type {@link Dimension}). Only applicable to Data Matrix now.
   */
  MIN_SIZE,

  /**
   * Specifies a maximum barcode size (type {@link Dimension}). Only applicable to Data Matrix now.
   */
  MAX_SIZE,

  /**
   * Specifies margin, in pixels, to use when generating the barcode. The meaning can vary
   * by format; for example it controls margin before and after the barcode horizontally for
   * most 1D formats. (Type {@link Integer}).
   */
  MARGIN,

  /**
   * Specifies whether to use compact mode for PDF417 (type {@link Boolean}).
   */
  PDF417_COMPACT,

  /**
   * Specifies what compaction mode to use for PDF417 (type
   * {@link com.google.zxing.pdf417.encoder.Compaction Compaction}).
   */
  PDF417_COMPACTION,

  /**
   * Specifies the minimum and maximum number of rows and columns for PDF417 (type
   * {@link com.google.zxing.pdf417.encoder.Dimensions Dimensions}).
   */
  PDF417_DIMENSIONS,

  /**
   * Specifies the required number of layers for an Aztec code:
   *   a negative number (-1, -2, -3, -4) specifies a compact Aztec code
   *   0 indicates to use the minimum number of layers (the default)
   *   a positive number (1, 2, .. 32) specifies a normaol (non-compact) Aztec code
   */
   AZTEC_LAYERS,
}
