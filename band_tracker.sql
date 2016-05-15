--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.2
-- Dumped by pg_dump version 9.5.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: bands; Type: TABLE; Schema: public; Owner: jacob
--

CREATE TABLE bands (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE bands OWNER TO jacob;

--
-- Name: bands_id_seq; Type: SEQUENCE; Schema: public; Owner: jacob
--

CREATE SEQUENCE bands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bands_id_seq OWNER TO jacob;

--
-- Name: bands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: jacob
--

ALTER SEQUENCE bands_id_seq OWNED BY bands.id;


--
-- Name: bands_venues; Type: TABLE; Schema: public; Owner: jacob
--

CREATE TABLE bands_venues (
    id integer NOT NULL,
    band_id integer,
    venue_id integer
);


ALTER TABLE bands_venues OWNER TO jacob;

--
-- Name: bands_venues_id_seq; Type: SEQUENCE; Schema: public; Owner: jacob
--

CREATE SEQUENCE bands_venues_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bands_venues_id_seq OWNER TO jacob;

--
-- Name: bands_venues_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: jacob
--

ALTER SEQUENCE bands_venues_id_seq OWNED BY bands_venues.id;


--
-- Name: venues; Type: TABLE; Schema: public; Owner: jacob
--

CREATE TABLE venues (
    id integer NOT NULL,
    description character varying
);


ALTER TABLE venues OWNER TO jacob;

--
-- Name: venues_id_seq; Type: SEQUENCE; Schema: public; Owner: jacob
--

CREATE SEQUENCE venues_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE venues_id_seq OWNER TO jacob;

--
-- Name: venues_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: jacob
--

ALTER SEQUENCE venues_id_seq OWNED BY venues.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: jacob
--

ALTER TABLE ONLY bands ALTER COLUMN id SET DEFAULT nextval('bands_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: jacob
--

ALTER TABLE ONLY bands_venues ALTER COLUMN id SET DEFAULT nextval('bands_venues_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: jacob
--

ALTER TABLE ONLY venues ALTER COLUMN id SET DEFAULT nextval('venues_id_seq'::regclass);


--
-- Data for Name: bands; Type: TABLE DATA; Schema: public; Owner: jacob
--

COPY bands (id, name) FROM stdin;
9	MGMT
10	Tiesto
\.


--
-- Name: bands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: jacob
--

SELECT pg_catalog.setval('bands_id_seq', 10, true);


--
-- Data for Name: bands_venues; Type: TABLE DATA; Schema: public; Owner: jacob
--

COPY bands_venues (id, band_id, venue_id) FROM stdin;
21	9	5
22	10	3
23	10	4
24	10	5
\.


--
-- Name: bands_venues_id_seq; Type: SEQUENCE SET; Schema: public; Owner: jacob
--

SELECT pg_catalog.setval('bands_venues_id_seq', 24, true);


--
-- Data for Name: venues; Type: TABLE DATA; Schema: public; Owner: jacob
--

COPY venues (id, description) FROM stdin;
1	
3	the new place
4	the other place
5	the lounge
\.


--
-- Name: venues_id_seq; Type: SEQUENCE SET; Schema: public; Owner: jacob
--

SELECT pg_catalog.setval('venues_id_seq', 5, true);


--
-- Name: bands_pkey; Type: CONSTRAINT; Schema: public; Owner: jacob
--

ALTER TABLE ONLY bands
    ADD CONSTRAINT bands_pkey PRIMARY KEY (id);


--
-- Name: bands_venues_pkey; Type: CONSTRAINT; Schema: public; Owner: jacob
--

ALTER TABLE ONLY bands_venues
    ADD CONSTRAINT bands_venues_pkey PRIMARY KEY (id);


--
-- Name: venues_pkey; Type: CONSTRAINT; Schema: public; Owner: jacob
--

ALTER TABLE ONLY venues
    ADD CONSTRAINT venues_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: jacob
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM jacob;
GRANT ALL ON SCHEMA public TO jacob;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

