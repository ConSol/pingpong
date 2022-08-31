CREATE TABLE public.games (
   id BIGINT CONSTRAINT upload__pk__id PRIMARY KEY,
   game_id VARCHAR(128) CONSTRAINT games__not_null__game_id NOT NULL,
   time_ping BIGINT CONSTRAINT games__not_null__time_ping NOT NULL,
   time_pong BIGINT CONSTRAINT games__not_null__time_pong NOT NULL,
   rounds_played INT CONSTRAINT games__not_null_rounds_played NOT NULL,
   winner VARCHAR(15)
);

CREATE SEQUENCE games__seq__id INCREMENT BY 1 OWNED BY public.games.id;