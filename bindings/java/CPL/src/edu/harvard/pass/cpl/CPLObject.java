package edu.harvard.pass.cpl;

/*
 * CPLObject.java
 * Core Provenance Library
 *
 * Copyright 2012
 *      The President and Fellows of Harvard College.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE UNIVERSITY OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * Contributor(s): Peter Macko
 */


import swig.direct.CPLDirect.*;


/**
 * A provenance object
 *
 * @author Peter Macko
 */
public class CPLObject {

	/// The null object
	private static final cpl_id_t nullId = CPLDirect.getCPL_NONE();

	/// The internal object ID
	private cpl_id_t id;

	/// The object originator (cache)
	private String originator = null;

	/// The object name (cache)
	private String name = null;

	/// The object type (cache)
	private String type = null;

	/// The object containter (cache)
	private CPLObject container = null;

	/// Whether the container is known
	private boolean knowContainer = false;


	/**
	 * Create an instance of CPLObject from its ID
	 *
	 * @param id the internal CPL object ID
	 */
	protected CPLObject(cpl_id_t id) {
		this.id = id;
	}


	/**
	 * Create a new CPLObject
	 *
	 * @param originator the originator
	 * @param name the object name
	 * @param type the object type
	 * @param container the object container
	 */
	public CPLObject(String originator, String name, String type,
			CPLObject container) {

		this.originator = originator;
		this.name = name;
		this.type = type;
		this.container = container;
		this.knowContainer = true;

		this.id = new cpl_id_t();
		int r = CPLDirect.cpl_create_object(originator, name, type,
				container == null ? nullId : container.id, this.id);
		CPLException.assertSuccess(r);
	}


	/**
	 * Create a new CPLObject
	 *
	 * @param originator the originator
	 * @param name the object name
	 * @param type the object type
	 * @param container the object container
	 */
	public CPLObject(String originator, String name, String type) {
		this(originator, name, type, null);
	}


	/**
	 * Lookup an existing object; return null if not found
	 *
	 * @return the object, or null if not found
	 */
	public static CPLObject tryLookup(String originator, String name,
			String type) {

		cpl_id_t id = new cpl_id_t();
		int r = CPLDirect.cpl_lookup_object(originator, name, type, id);

		if (CPLException.isError(r)) {
			if (r == CPLDirect.CPL_E_NOT_FOUND) return null;
			throw new CPLException(r);
		}

		CPLObject o = new CPLObject(id);
		o.originator = originator;
		o.name = name;
		o.type = type;

		return o;
	}


	/**
	 * Lookup an existing object
	 *
	 * @return the object
	 */
	public static CPLObject lookup(String originator, String name,
			String type) {
		CPLObject o = tryLookup(originator, name, type);
		if (o == null) throw new CPLException(CPLDirect.CPL_E_NOT_FOUND);
		return o;
	}


	/**
	 * Determine whether this and the other object are equal
	 *
	 * @param other the other object
	 * @return true if they are equal
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof CPLObject) {
			CPLObject o = (CPLObject) other;
			return o.id.getHi().equals(this.id.getHi())
				&& o.id.getLo().equals(this.id.getLo());
		}
		else {
			return false;
		}
	}


	/**
	 * Compute the hash code of this object
	 *
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		return id.getHi().hashCode() ^ id.getLo().hashCode();
	}


	/**
	 * Return a string representation of the object. Note that this is based
	 * on the internal object ID, since the name might not be known.
	 *
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return id.getHi().toString(16) + ":" + id.getLo().toString(16);
	}
}
