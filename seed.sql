-- Insert into stations
INSERT INTO public.stations (name, number_of_platforms) VALUES
('New Delhi', 0),
('Howrah', 0),
('Chennai Central', 0);

-- Insert into platforms (platform_name must be unique per station)
INSERT INTO public.platforms (station_id, platform_name) VALUES
(1, 'Platform 1'),
(1, 'Platform 2'),
(2, 'Platform 1');

-- Insert into trains
INSERT INTO public.trains (train_number, color, priority) VALUES
('12345', 'Red', 1),
('67890', 'Blue', 2),
('24680', 'Green', 0);
