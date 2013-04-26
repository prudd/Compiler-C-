void stupidMethod(int zebra, int walrus, int kitten)
{
	int a[3];
	a[0] = 3;
	a[1] = 4;
	a[2] = 5;
	if (kitten+zebra+walrus<=zebra+walrus*kitten)
		zebra = walrus;
	if (kitten+walrus==zebra)
	{
		kitten = walrus+zebra;
		walrus = zebra-walrus;
		zebra = zebra/walrus;
	}
	else
	{
		zebra = walrus*walrus;
	}

	if (kitten<=zebra)
		zebra = walrus;
	if (kitten>=walrus)
		walrus = kitten;
	if (zebra!=kitten)
		kitten = kitten+1;
	if (kitten<zebra)
		zebra = walrus;
	if (zebra>walrus-2)
		walrus = zebra;
	while (zebra>walrus)
		zebra = zebra-3;
}
