use trivio;

INSERT INTO quiz(quiz_id, quiz_title, is_live)
VALUES (3, 'Pokemon Fanatic', 1)
;


INSERT INTO question(question_id, quiz_id, question_number, question_text)
VALUES (11, 3, 1, 'What type of Pokemon is Happiny?')
     , (12, 3, 2, 'Which Pokémon is known as the ''Fire-type starter'' from Generation 1?')
     , (13, 3, 3, 'Which Pokémon evolves from Eevee when exposed to a Water Stone?')
     , (14, 3, 4, 'What is the only dual-type Electric/Dragon Pokémon as of Generation 8?')
     , (15, 3, 5, 'In which region do you find the Pokémon Grookey, Scorbunny, and Sobble as starters?')
     , (16, 3, 6, 'Which Pokémon has the highest base stat total of all non-legendary, non-Mega Evolved Pokémon?');

INSERT INTO answer(question_id, answer_text, is_correct)
VALUES (11, 'Psychic', 0)
     , (11, 'Normal', 1)
     , (11, 'Fairy', 0)
     , (11, 'Poison', 0)

     , (12, 'Charmander', 1)
     , (12, 'Fuecoco', 0)
     , (12, 'Cyndaquil', 0)
     , (12, 'Tepig', 0)
     
     , (13, 'Flareon', 0)
     , (13, 'Umbreon', 0)
     , (13, 'Vaporeon', 1)
     , (13, 'Watereon', 0)
     
     , (14, 'Dracozolt', 1)
     , (14, 'Zekrom', 0)
     , (14, 'Tapu Koko', 0)
     , (14, 'Dragonite', 0)
	 
     , (15, 'Sinnoh', 0)
     , (15, 'Alola', 0)
     , (15, 'Galar', 1)
     , (15, 'Kalos', 0)
     	 
     , (16, 'Tyranitar', 0)
     , (16, 'Garchomp', 0)
     , (16, 'Dragonite', 0)
     , (16, 'Slaking', 1);

Error Code: 1452. Cannot add or update a child row: a foreign key constraint fails (`trivio`.`question`, CONSTRAINT `question_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`))


