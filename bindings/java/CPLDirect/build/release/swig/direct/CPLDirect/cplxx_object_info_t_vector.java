/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.10
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package swig.direct.CPLDirect;

public class cplxx_object_info_t_vector {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected cplxx_object_info_t_vector(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(cplxx_object_info_t_vector obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CPLDirectJNI.delete_cplxx_object_info_t_vector(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public cplxx_object_info_t_vector() {
    this(CPLDirectJNI.new_cplxx_object_info_t_vector__SWIG_0(), true);
  }

  public cplxx_object_info_t_vector(long n) {
    this(CPLDirectJNI.new_cplxx_object_info_t_vector__SWIG_1(n), true);
  }

  public long size() {
    return CPLDirectJNI.cplxx_object_info_t_vector_size(swigCPtr, this);
  }

  public long capacity() {
    return CPLDirectJNI.cplxx_object_info_t_vector_capacity(swigCPtr, this);
  }

  public void reserve(long n) {
    CPLDirectJNI.cplxx_object_info_t_vector_reserve(swigCPtr, this, n);
  }

  public boolean isEmpty() {
    return CPLDirectJNI.cplxx_object_info_t_vector_isEmpty(swigCPtr, this);
  }

  public void clear() {
    CPLDirectJNI.cplxx_object_info_t_vector_clear(swigCPtr, this);
  }

  public void add(cplxx_object_info_t x) {
    CPLDirectJNI.cplxx_object_info_t_vector_add(swigCPtr, this, cplxx_object_info_t.getCPtr(x), x);
  }

  public cplxx_object_info_t get(int i) {
    return new cplxx_object_info_t(CPLDirectJNI.cplxx_object_info_t_vector_get(swigCPtr, this, i), false);
  }

  public void set(int i, cplxx_object_info_t val) {
    CPLDirectJNI.cplxx_object_info_t_vector_set(swigCPtr, this, i, cplxx_object_info_t.getCPtr(val), val);
  }

}