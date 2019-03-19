readFile <- function(file)
{
	str <- readLines(file)
	str <- paste(str, collapse="\n")
	return(str)
}