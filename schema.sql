--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: platforms; Type: TABLE; Schema: public; Owner: postgres
--
SET search_path TO public;

-- Explicitly create the public schema (if not already created)
CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.platforms (
    platform_id integer NOT NULL,
    station_id integer NOT NULL,
    platform_name character varying(25) NOT NULL
);


ALTER TABLE public.platforms OWNER TO postgres;

--
-- Name: platforms_platform_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.platforms_platform_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.platforms_platform_id_seq OWNER TO postgres;

--
-- Name: platforms_platform_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.platforms_platform_id_seq OWNED BY public.platforms.platform_id;


--
-- Name: stations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stations (
    station_id integer NOT NULL,
    name character varying(50) NOT NULL,
    number_of_platforms integer
);


ALTER TABLE public.stations OWNER TO postgres;

--
-- Name: stations_station_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.stations_station_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.stations_station_id_seq OWNER TO postgres;

--
-- Name: stations_station_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.stations_station_id_seq OWNED BY public.stations.station_id;


--
-- Name: trains; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trains (
    train_id integer NOT NULL,
    train_number character varying(25),
    color character varying(25),
    priority integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.trains OWNER TO postgres;

--
-- Name: trains_train_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.trains_train_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.trains_train_id_seq OWNER TO postgres;

--
-- Name: trains_train_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.trains_train_id_seq OWNED BY public.trains.train_id;


--
-- Name: platforms platform_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.platforms ALTER COLUMN platform_id SET DEFAULT nextval('public.platforms_platform_id_seq'::regclass);


--
-- Name: stations station_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stations ALTER COLUMN station_id SET DEFAULT nextval('public.stations_station_id_seq'::regclass);


--
-- Name: trains train_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trains ALTER COLUMN train_id SET DEFAULT nextval('public.trains_train_id_seq'::regclass);


--
-- Name: platforms platforms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.platforms
    ADD CONSTRAINT platforms_pkey PRIMARY KEY (platform_id);


--
-- Name: platforms platforms_station_id_platform_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.platforms
    ADD CONSTRAINT platforms_station_id_platform_name_key UNIQUE (station_id, platform_name);


--
-- Name: stations stations_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stations
    ADD CONSTRAINT stations_name_key UNIQUE (name);


--
-- Name: stations stations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stations
    ADD CONSTRAINT stations_pkey PRIMARY KEY (station_id);


--
-- Name: trains trains_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trains
    ADD CONSTRAINT trains_pkey PRIMARY KEY (train_id);


--
-- Name: platforms platforms_station_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.platforms
    ADD CONSTRAINT platforms_station_id_fkey FOREIGN KEY (station_id) REFERENCES public.stations(station_id) ON DELETE CASCADE;

CREATE OR REPLACE FUNCTION update_platform_count()
RETURNS TRIGGER AS $$
BEGIN
  -- Use OLD.station_id during DELETE, NEW.station_id during INSERT
  IF TG_OP = 'DELETE' THEN
    UPDATE stations
    SET number_of_platforms = (
      SELECT COUNT(*) FROM platforms WHERE station_id = OLD.station_id
    )
    WHERE station_id = OLD.station_id;

    RETURN OLD;

  ELSIF TG_OP = 'INSERT' THEN
    UPDATE stations
    SET number_of_platforms = (
      SELECT COUNT(*) FROM platforms WHERE station_id = NEW.station_id
    )
    WHERE station_id = NEW.station_id;

    RETURN NEW;
  END IF;
END;
$$ LANGUAGE plpgsql;


-- Triggers after insert and delete on platforms
CREATE TRIGGER trg_update_platform_count_after_insert
AFTER INSERT ON platforms
FOR EACH ROW
EXECUTE FUNCTION update_platform_count();

CREATE TRIGGER trg_update_platform_count_after_delete
AFTER DELETE ON platforms
FOR EACH ROW
EXECUTE FUNCTION update_platform_count();

-- Corrected trigger for updating station_id
CREATE OR REPLACE FUNCTION update_station_id_function()
RETURNS TRIGGER AS $$
BEGIN
    -- Check if station_id has actually changed
    IF OLD.station_id != NEW.station_id THEN
        -- Update all platforms that had the old station_id
        UPDATE platforms
        SET station_id = NEW.station_id
        WHERE station_id = OLD.station_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_station_id
AFTER UPDATE ON platforms
FOR EACH ROW
EXECUTE FUNCTION update_station_id_function();

--
-- PostgreSQL database dump complete
--

