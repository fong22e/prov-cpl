#include "stdafx.h"
#include "cpl-private.h"
#include "cpl-platform.h"
#include <private/cpl-platform.h>

#include <Rcpp.h>
using namespace Rcpp;

#ifdef __APPLE__
	#include <mach-o/dyld.h>
#endif

/***************************************************************************/
/** Initialization and Cleanup                                            **/
/***************************************************************************/

// TODO: copy code over from prov_json_handler.cpp

/***************************************************************************/
/** Public API: Helpers                                                   **/
/***************************************************************************/

// [[Rcpp::export]]
std::string cpl_error_string_r(const unsigned long long code)
{
	std::string output = cpl_error_string(code);
	return output;
}
